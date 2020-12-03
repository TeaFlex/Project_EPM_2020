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

        if(dataExists(emp.getEmpID()))
            throw new Exception(String.format("This employee (ID: %d) already exist.", emp.getEmpID()));

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
    public void save(int id, Receipt receipt) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if(!(getData(id).getPaymentClassification() instanceof CommissionClassification))
            throw new Exception("This employee can't receive receipt.");

        connect();



        disconnect();
    }

    @Override
    public void save(int id, TimeCard timeCard) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if(!(getData(id).getPaymentClassification() instanceof HourlyClassification))
            throw new Exception("This employee can't receive timecard.");

        connect();
        disconnect();
    }

    @Override
    public void delete(int id) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

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

        if(!this.dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

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

        query = String.format("SELECT E.nameemp, E.addressemp, T.*, G.* from EMPLOYEES E, " +
                "%s T, %s G WHERE E.empid=%d AND T.empid = E.empid AND G.empid = E.empid;", type, method, id);
        rs = statement.executeQuery(query);
        rs.next();

        response.setId(rs.getInt("empid"));
        response.setName(rs.getString("nameemp"));
        response.setAddress(rs.getString("addressemp"));

        switch (method.replace("Method", "")) {
            case "Mail":
                response.setEmail(rs.getString("email"));
                break;
            case "DirectDeposit":
                response.setIban(rs.getString("iban"));
                response.setBank(rs.getString("bank"));
                break;
        }

        statement = null;
        disconnect();
        return response.toEmployee();
    }

    @Override
    public boolean dataExists(int id) throws Exception {
        connect();
        String query = String.format("Select * from Employees where empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean result = (rs.next());
        statement = null;
        disconnect();
        return result;
    }

}
