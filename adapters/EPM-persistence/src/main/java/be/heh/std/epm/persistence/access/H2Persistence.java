package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.*;

import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

        boolean isIdZero = emp.getEmpID() == 0;

        String query = "insert into EMPLOYEES " +
                "(name, address, paymentClassification, paymentSchedule, PaymentMethod" +
                ((isIdZero) ? ") VALUES (?, ?, ?, ?, ?);":", empID) VALUES (?, ?, ?, ?, ?, ?);");

        //System.out.println(query);
        String type = emp.getPaymentClassification().getClass().getSimpleName();
        String method = emp.getPaymentMethod().getClass().getSimpleName();
        String schedule = emp.getPaymentSchedule().getClass().getSimpleName();
        int id = emp.getEmpID();

        PreparedStatement prep = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        prep.setString(1, emp.getName());
        prep.setString(2, emp.getAddress());
        prep.setString(3, type);
        prep.setString(4, schedule);
        prep.setString(5, method);
        if(!isIdZero) prep.setInt(6, id);

        int rowsAffected = prep.executeUpdate();

        if(isIdZero) {
            ResultSet rs = prep.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        }

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

        prep.setInt(1, id);
        prep.setDouble(2, emp.getPaymentClassification().getSalary());

        rowsAffected = prep.executeUpdate();

        //Query on any Method table

        query = String.format("insert into %s (empid, ", method);

        if(method.equals("DirectDepositMethod")) {
            query += "iban, bank) VALUES (?, ?, ?);";
            prep = getConnection().prepareStatement(query);
            prep.setInt(1, id);
            String[] infos = {
                    ((DirectDepositMethod)emp.getPaymentMethod()).getBank(),
                    ((DirectDepositMethod)emp.getPaymentMethod()).getIban()
            };
            prep.setString(2, infos[1]);
            prep.setString(3, infos[0]);
        } else if(method.equals("MailMethod")) {
            query += "email) VALUES (?, ?);";
            prep = getConnection().prepareStatement(query);
            prep.setInt(1, id);
            String email = ((MailMethod)emp.getPaymentMethod()).getEmail();
            prep.setString(2,email);
        }

        rowsAffected = prep.executeUpdate();
    }

    @Override
    public void saveReceipt(int id, Receipt receipt) throws Exception {

        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if (!(getData(id).getPaymentClassification() instanceof CommissionClassification))
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

        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        if (!(getData(id).getPaymentClassification() instanceof HourlyClassification))
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
    public void deleteEmployee(int id) throws Exception {

        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        String query = "Delete from Employees where empid = ?;";

        PreparedStatement prep = getConnection().prepareStatement(query);

        prep.setInt(1, id);

        int r = prep.executeUpdate();

    }

    @Override
    public void updateAddress(int id, String newAddress) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        String query = "UPDATE Employees SET Address = ? WHERE empid = ?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, newAddress);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    @Override
    public void updateName(int id, String newName) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        String query = "UPDATE Employees SET Name = ? WHERE empid = ?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    @Override
    public void updateToCommissioned(int id, double salary, double commissionRate) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        deletePaymentClassification(id);

        String query = "UPDATE Employees SET paymentClassification = 'CommissionClassification' WHERE empid = " + id;
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);

        replacePaymentSchedule(id, BiweeklyPaymentSchedule.class);

        query = "INSERT INTO CommissionClassification VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setDouble(2, salary);
        preparedStatement.setDouble(3, commissionRate);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateToHourly(int id, double rate) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        deletePaymentClassification(id);

        String query = "UPDATE Employees SET paymentClassification = 'HourlyClassification' WHERE empid = " + id;
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);

        replacePaymentSchedule(id, WeeklyPaymentSchedule.class);

        query = "INSERT INTO HourlyClassification VALUES (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setDouble(2, rate);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateToSalaried(int id, double salary) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        deletePaymentClassification(id);

        String query = "UPDATE Employees SET paymentClassification = 'SalariedClassification' WHERE empid = " + id;
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);

        replacePaymentSchedule(id, MonthlyPaymentSchedule.class);

        query = "INSERT INTO SalariedClassification VALUES (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setDouble(2, salary);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        deletePaymentMethod(id);

        String query = "UPDATE Employees SET paymentMethod = 'DirectDepositMethod' WHERE empid = " + id;
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);

        query = "INSERT INTO DirectDepositMethod VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, iban);
        preparedStatement.setString(3, bank);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateToMailMethod(int id, String email) throws Exception {
        if (!dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));
        deletePaymentMethod(id);

        String query = "UPDATE Employees SET paymentMethod = 'MailMethod' WHERE empid = " + id;
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);

        query = "INSERT INTO MailMethod VALUES (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();
    }

    @Override
    public Employee getData(int id) throws Exception {

        if (!this.dataExists(id))
            throw new Exception(String.format("This employee (ID: %d) does not exist.", id));

        PaymentSchedule paymentSchedule = null;
        PaymentClassification paymentClassification = null;
        PaymentMethod paymentMethod = null;

        String query = String.format("SELECT paymentClassification, paymentMethod, paymentSchedule " +
                "FROM EMPLOYEES WHERE empid=%d", id);
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
                for (Receipt r : this.getReceipts(response.getEmpID()))
                    ((CommissionClassification) paymentClassification).addReceipt(r);
                break;
            case "Hourly":
                paymentClassification = new HourlyClassification(p);
                for (TimeCard c : this.getTimeCards(response.getEmpID()))
                    ((HourlyClassification) paymentClassification).addTimeCard(c);
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

        switch (method.replace("Method", "")) {
            case "Mail":
                paymentMethod = new MailMethod(rs.getString("email"));
                break;
            case "DirectDeposit":
                paymentMethod = new DirectDepositMethod(rs.getString("bank"),
                        rs.getString("iban"));
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

    private void deletePaymentClassification(int id) throws Exception {
        String query = String.format("Select paymentClassification from Employees " +
                "where empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String type = resultSet.getString(1);
        statement = null;

        //deletePaymentSchedule(id);

        query = String.format("Delete from %s where empid = ?;", type);
        PreparedStatement prep = getConnection().prepareStatement(query);
        prep.setInt(1, id);
        int r = prep.executeUpdate();
    }

    private void deletePaymentSchedule(int id) throws Exception {
        String query = String.format("Select paymentSchedule from Employees " +
                "where empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String schedule = resultSet.getString(1);
        statement = null;

        query = String.format("Delete from %s where empid = ?;", schedule);
        PreparedStatement prep = getConnection().prepareStatement(query);
        prep.setInt(1, id);
        int r = prep.executeUpdate();
    }

    private void deletePaymentMethod(int id) throws Exception {
        String query = String.format("Select paymentMethod from Employees " +
                "where empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String method = resultSet.getString(1);
        statement = null;

        query = String.format("Delete from %s where empid = ?;", method);
        PreparedStatement prep = getConnection().prepareStatement(query);
        prep.setInt(1, id);
        int r = prep.executeUpdate();
    }

    private ArrayList<TimeCard> getTimeCards(int id) throws Exception {
        ArrayList<TimeCard> response = new ArrayList<>();
        String query = String.format("SELECT * FROM TimeCards WHERE empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        TimeCard timeCard;
        while(resultSet.next()) {
            timeCard = new TimeCard(resultSet.getDate("tDate").toLocalDate(),
                    resultSet.getDouble("hours"));
            response.add(timeCard);
        }
        statement = null;
        return response;
    }

    private ArrayList<Receipt> getReceipts(int id) throws Exception {
        ArrayList<Receipt> response = new ArrayList<>();
        String query = String.format("SELECT * FROM Receipts WHERE empid = %d;", id);
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Receipt receipt;
        while(resultSet.next()) {
            receipt = new Receipt(resultSet.getDate("tDate").toLocalDate(),
                    resultSet.getDouble("price"));
            response.add(receipt);
        }
        statement = null;
        return response;
    }

    private void replacePaymentSchedule(int id, Class newSchedule) throws Exception {
        String query = String.format("UPDATE Employees SET paymentSchedule = '%s' WHERE empid = %d;",
                newSchedule.getSimpleName(), id);
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(query);
    }
}
