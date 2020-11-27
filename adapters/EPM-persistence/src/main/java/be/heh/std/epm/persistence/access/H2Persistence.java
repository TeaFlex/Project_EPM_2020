package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.data.DataCommissionEmployee;
import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataHourlyEmployee;
import be.heh.std.epm.application.data.DataSalariedEmployee;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.SalariedClassification;
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
        String query = "insert into EMPLOYEES " +
                "(EmpID, NameEmp, AddressEmp, PaymentClassification, PaymentMethod, PaymentSchedule) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, emp.getEmpID());
        prep.setString(2, emp.getName());
        prep.setString(3, emp.getAddress());
        prep.setString(4, emp.getPaymentClassification().getClass().getSimpleName());
        prep.setString(5, emp.getPaymentMethod().getClass().getSimpleName());
        prep.setString(6, emp.getPaymentSchedule().getClass().getSimpleName());

        int rowsAffected = prep.executeUpdate();

        //query = String.format("insert into %s (%s)", emp.getPaymentClassification(), );

        disconnect();
    }

    @Override
    public void delete(int id) throws Exception {
        //TODO
    }

    @Override
    public void replace(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public Employee getData(int id) throws Exception {
        DataEmployee response = null;
        String type = "";
        connect();
        String query = String.format("SELECT PaymentClassification FROM EMPLOYEES WHERE empid=%d", id);
        Statement statement = getConnection().createStatement();

        ResultSet rs = statement.executeQuery(query);
        rs.next();
        type = rs.getString("PaymentClassification");

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

        rs =null;
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
