package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.persistence.access.DBPersistence;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class H2Persistence extends DBPersistence {

    public H2Persistence (String server, String username, String password) {
        super("h2", server, username, password);
    }

    @Override
    public void save(Employee emp) throws Exception {
        connect();
        /*String query = "insert into EMPLOYEES " +
                "(EmpID, NameEmp, AddressEmp, PaymentClassification, PaymentMethod, PaymentSchedule) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement prep = connection.prepareStatement(query);

        prep.setInt(1, emp.getEmpID());
        prep.setString(2, emp.getName());
        prep.setString(3, emp.getAddress());
        prep.setString(4, emp.getPaymentClassification().getClass().getName());
        prep.setString(5, emp.getPaymentMethod().getClass().getName());
        prep.setString(6, emp.getPaymentSchedule().getClass().getName());

        int rowsAffected = prep.executeUpdate();*/

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
        //TODO
        return null;
    }

}
