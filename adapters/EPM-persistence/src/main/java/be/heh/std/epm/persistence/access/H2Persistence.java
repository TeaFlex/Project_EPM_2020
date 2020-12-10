package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.*;

import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2Persistence extends SQLikePersistence {

    public H2Persistence () throws Exception {
        //for test purpose only
        super("h2", "mem:", "", "");
        String sqlScript = System.getProperty("user.dir") + "/src/main/resources/schema.sql";
        if(new File(sqlScript).exists()) {
            Statement s = getConnection().createStatement();
            s.execute(String.format("RUNSCRIPT from '%s';", sqlScript));
        }
    }

    public H2Persistence (String server, String username, String password) {
        super("h2", String.format("%s;AUTO_SERVER=TRUE", server), username, password);
    }

    @Override
    public void saveEmployee(Employee emp) throws Exception {

        if(dataExists(emp.getEmpID()))
            throw new Exception(String.format("This employee (ID: %d) already exist.", emp.getEmpID()));


        //Query on Employees table

        String query = "insert into EMPLOYEES " +
                "(empID, name, address, paymentClassification, paymentSchedule, PaymentMethod) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        String type = emp.getPaymentClassification().getClass().getSimpleName();
        String method = emp.getPaymentMethod().getClass().getSimpleName();
        String schedule = emp.getPaymentSchedule().getClass().getSimpleName();

        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, emp.getEmpID());
        prep.setString(2, emp.getName());
        prep.setString(3, emp.getAddress());
        prep.setString(4, type);
        prep.setString(5, schedule);
        prep.setString(6, method);

        int rowsAffected = prep.executeUpdate();

        //Query on any Classification table

        query = String.format("insert into %s (empid, salary)" +
                "VALUES (?, ?);", type);
        prep = getConnection().prepareStatement(query);

        if(type.equals("CommissionClassification")) {
            query = String.format("insert into %s (empid, salary, rate)" +
                    "VALUES (?, ?, ?);", type);
            prep = getConnection().prepareStatement(query);
            double rate = ((CommissionClassification) emp.getPaymentClassification()).getCommissionRate();
            prep.setDouble(3, rate);
        }

        prep.setInt(1, emp.getEmpID());
        prep.setDouble(2, emp.getPaymentClassification().getSalary());

        rowsAffected = prep.executeUpdate();

        //Query on any Method table

        query = String.format("insert into %s (empid, ", method);

        if(method.equals("DirectDepositMethod")) {
            query += "iban, bank) VALUES (?, ?, ?);";
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
    }

    @Override
    public void saveReceipt(int id, Receipt receipt) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if(!(getData(id).getPaymentClassification() instanceof CommissionClassification))
            throw new Exception("This employee can't receive receipt.");


        String query = "Insert into Receipts (empid, tdate, price) VALUES (?, ?, ?);";
        PreparedStatement prep = getConnection().prepareStatement(query);
        Date date = Date.valueOf(receipt.getDate());

        prep.setInt(1, id);
        prep.setDate(2, date);
        prep.setDouble(3, receipt.getPrice());

        int rowsAffected = prep.executeUpdate();

    }

    @Override
    public void saveTimeCard(int id, TimeCard timeCard) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if(!(getData(id).getPaymentClassification() instanceof HourlyClassification))
            throw new Exception("This employee can't receive timecard.");

        String query = "Insert into Timecards (empid, tdate, hours) VALUES (?, ?, ?);";
        PreparedStatement prep = getConnection().prepareStatement(query);
        Date date = Date.valueOf(timeCard.getDate());

        prep.setInt(1, id);
        prep.setDate(2, date);
        prep.setDouble(3, timeCard.getHours());

        int rowsAffected = prep.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {

        if(!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        String query = "Delete from Employees where empid = ?;";

        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, id);

        int r = prep.executeUpdate();

    }

    @Override
    public void replace(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public Employee getData(int id) throws Exception {

        if(!this.dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        PaymentSchedule paymentSchedule = null;
        PaymentClassification paymentClassification = null;
        PaymentMethod paymentMethod = null;

        String query = String.format("SELECT paymentClassification, paymentMethod, paymentSchedule FROM EMPLOYEES WHERE empid=%d", id);
        Statement statement = getConnection().createStatement();

        ResultSet rs = statement.executeQuery(query);
        rs.next();
        String type = rs.getString("paymentClassification");
        String method = rs.getString("paymentMethod");
        String schedule = rs.getString("paymentSchedule");

        query = String.format("SELECT E.name, E.address, T.*, G.* from EMPLOYEES E, " +
                "%s T, %s G WHERE E.empid=%d AND T.empid = E.empid AND G.empid = E.empid;", type, method, id);
        rs = statement.executeQuery(query);
        rs.next();

        Employee response = new Employee(rs.getInt("empid"),
                rs.getString("name"),
                rs.getString("address"));

        double p = rs.getDouble("salary");

        switch (type.replace("Classification", "")) {
            case "Salaried":
                paymentClassification = new SalariedClassification(p);
                break;
            case "Commission":
                paymentClassification = new CommissionClassification(p, rs.getDouble("rate"));
                break;
            case "Hourly":
                paymentClassification = new HourlyClassification(p);
                break;
        }

        switch (schedule.replace("PaymentSchedule", "")) {
            case "Monthly":
                paymentSchedule = new MonthlyPaymentSchedule();
                break;
            case "Weekly":
                paymentSchedule = new WeeklyPaymentSchedule();
                break;
            case "Biweekly":
                paymentSchedule = new BiweeklyPaymentSchedule();
                break;
        }

        //TODO: faire une query qui prend toutes les timecard/Receipts et les rajoute à la collection de l'objet

        switch (method.replace("Method", "")) {
            case "Mail":
                paymentMethod = new MailMethod(rs.getString("email"));
                break;
            case "DirectDeposit":
                paymentMethod = new DirectDepositMethod(rs.getString("iban"),
                        rs.getString("bank"));
                break;
        }

        response.setPaymentClassification(paymentClassification);
        response.setPaymentMethod(paymentMethod);
        response.setPaymentSchedule(paymentSchedule);

        statement = null;
        return response;
    }

    @Override
    public boolean dataExists(int id) throws Exception {
        String query = String.format("Select * from Employees where empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean result = (rs.next());
        statement = null;
        return result;
    }

}
