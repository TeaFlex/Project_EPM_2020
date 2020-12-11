package be.heh.std.epm.persistence.access;

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
        database.put(emp.getEmpID(), emp);
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
    public void delete(int id) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee does not exist.");
        database.remove(id);
    }

    @Override
    public void updateAddress(int id, String newAddress) throws Exception {

    }

    @Override
    public void updateName(int id, String newName) throws Exception {

    }

    @Override
    public void updateToCommissioned(int id, double salary, double commissionRate) throws Exception {

    }

    @Override
    public void updateToHourly(int id, double rate) throws Exception {

    }

    @Override
    public void updateToSalaried(int id, double salary) throws Exception {

    }

    @Override
    public void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception {

    }

    @Override
    public void updateToMailMethod(int id, String email) throws Exception {

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
