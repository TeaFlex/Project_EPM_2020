package be.heh.std.epm.application.test_adapter;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;

import java.util.HashMap;

public class ListPersistence implements OutPersistence {

    //"database" for tests
    private HashMap<Integer, Employee> database = new HashMap<>();

    @Override
    public void saveEmployee(Employee emp) throws Exception {
        if(database.containsKey(emp.getEmpID()))
            throw new Exception(String.format("This employee (ID:%d) already exists.", emp.getEmpID()));
        int id = (emp.getEmpID() <= 0)? database.size()+1: emp.getEmpID();
        database.put(id, emp);
    }

    @Override
    public void saveReceipt(int id, Receipt receipt) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee doesn't exist.");
        if(!(database.get(id).getPaymentClassification() instanceof CommissionClassification))
            throw new Exception("You can't post receipt for this type of employee.");
        ((CommissionClassification) database.get(id).getPaymentClassification()).addReceipt(receipt);
    }

    @Override
    public void saveTimeCard(int id, TimeCard timeCard) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee doesn't exist.");
        if(!(database.get(id).getPaymentClassification() instanceof HourlyClassification))
            throw new Exception("You can't post receipt for this type of employee.");
        ((HourlyClassification) database.get(id).getPaymentClassification()).addTimeCard(timeCard);
    }


    @Override
    public void deleteEmployee(int id) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.remove(id);
    }

    @Override
    public void updateAddress(int id, String newAddress) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setAddress(newAddress);
    }

    @Override
    public void updateName(int id, String newName) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setName(newName);
    }

    @Override
    public void updateToCommissioned(int id, double salary, double commissionRate) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setPaymentClassification(new CommissionClassification(salary, commissionRate));
        database.get(id).setPaymentSchedule(new BiweeklyPaymentSchedule());
    }

    @Override
    public void updateToHourly(int id, double rate) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setPaymentClassification(new HourlyClassification(rate));
        database.get(id).setPaymentSchedule(new WeeklyPaymentSchedule());
    }

    @Override
    public void updateToSalaried(int id, double salary) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setPaymentClassification(new SalariedClassification(salary));
        database.get(id).setPaymentSchedule(new MonthlyPaymentSchedule());
    }

    @Override
    public void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setPaymentMethod(new DirectDepositMethod(bank, iban));
    }

    @Override
    public void updateToMailMethod(int id, String email) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.get(id).setPaymentMethod(new MailMethod(email));
    }

    @Override
    public Employee getData(int id) throws Exception {
        if(!database.containsKey(id)) throw new Exception("This employee does not exist.");
        return (Employee) database.get(id);
    }

    @Override
    public boolean dataExists(int id) throws Exception {
        return database.containsKey(id);
    }

    public HashMap<Integer, Employee> getDatabase() {
        return this.database;
    }
}
