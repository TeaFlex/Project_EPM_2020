package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.data.DataCommissionEmployee;
import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataHourlyEmployee;
import be.heh.std.epm.application.data.DataSalariedEmployee;
import be.heh.std.epm.domain.*;
import be.heh.std.epm.persistence.access.DBPersistence;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2Persistence extends DBPersistence {

    public H2Persistence (String server, String username, String password) {
        super("h2", String.format("%s;AUTO_SERVER=TRUE", server), username, password);
    }

    @Override
    public void save(Employee emp) throws Exception {
        connect();

        //Query on Employees table

        String query = "insert into EMPLOYEES " +
                "(EmpID, NameEmp, AddressEmp, type, PaymentMethod) " +
                "VALUES (?, ?, ?, ?, ?);";
        String type = emp.getPaymentClassification().getClass().getSimpleName();
        String method = emp.getPaymentMethod().getClass().getSimpleName();

        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, emp.getEmpID());
        prep.setString(2, emp.getName());
        prep.setString(3, emp.getAddress());
        prep.setString(4, type);
        prep.setString(5, method);

        int rowsAffected = prep.executeUpdate();

        //Query on any Classification table

        query = String.format("insert into %s (EmpID, salary)" +
                "VALUES (?, ?);", type);
        prep = getConnection().prepareStatement(query);

        if(type.equals("CommissionClassification")) {
            query = String.format("insert into %s (EmpID, salary, rate)" +
                    "VALUES (?, ?, ?);", type);
            prep = getConnection().prepareStatement(query);
            double rate = ((CommissionClassification) emp.getPaymentClassification()).getCommissionRate();
            prep.setDouble(3, rate);
        }

        prep.setInt(1, emp.getEmpID());
        prep.setDouble(2, emp.getPaymentClassification().getSalary());

        rowsAffected = prep.executeUpdate();

        //Query on any Method table

        query = String.format("insert into %s (EmpID, ", method);

        if(method.equals("DirectDepositMethod")) {
            query += "Iban, Bank) VALUES (?, ?, ?);";
            prep = getConnection().prepareStatement(query);
            prep.setInt(1, emp.getEmpID());
            String[] infos = {
                    ((DirectDepositMethod)emp.getPaymentMethod()).getBank(),
                    ((DirectDepositMethod)emp.getPaymentMethod()).getIban()
            };
            prep.setString(2, infos[0]);
            prep.setString(3, infos[1]);
        }
        else if(method.equals("MailMethod")) {
            query += "email) VALUES (?, ?);";
            prep = getConnection().prepareStatement(query);
            prep.setInt(1, emp.getEmpID());
            String email = ((MailMethod)emp.getPaymentMethod()).getEmail();
            prep.setString(2,email);
        }

        rowsAffected = prep.executeUpdate();

        disconnect();
    }

    @Override
    public void delete(int id) throws Exception {
        connect();

        String query = "Delete from Employees where EmpId = ?;";

        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, id);

        int r = prep.executeUpdate();

        disconnect();
    }

    @Override
    public void replace(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public Employee getData(int id) throws Exception {
        DataEmployee response = null;
        connect();
        String query = String.format("SELECT type, paymentmethod FROM EMPLOYEES WHERE empid=%d", id);
        Statement statement = getConnection().createStatement();

        ResultSet rs = statement.executeQuery(query);
        rs.next();
        String type = rs.getString("type");
        String method = rs.getString("paymentMethod");

        switch (type.replace("Classification", "")) {
            case "Salaried":
                response = new DataSalariedEmployee();
                break;
            case "Commission":
                response = new DataCommissionEmployee();
                break;
            case "Hourly":
                response = new DataHourlyEmployee();
                break;
        }

        query = String.format("SELECT E.nameemp, E.addressemp, T.* from EMPLOYEES E, " +
                "%s T WHERE T.empid = %d", type, id);
        rs = statement.executeQuery(query);
        rs.next();
        System.out.println(query);
        response.setId(rs.getInt("EMPID"));
        response.setName(rs.getString("NAMEEMP"));
        response.setAddress(rs.getString("addressEmp"));


        statement =null;
        disconnect();
        return response.toEmployee();
    }

}
