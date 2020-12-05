package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;

import java.util.HashMap;

public class ListPersistence implements OutPersistence {

    //"database" for tests
    private HashMap<Integer, Employee> database = new HashMap<>();

    @Override
    public void save(Employee emp) throws Exception {
        if(database.containsKey(emp.getEmpID()))
            throw new Exception(String.format("This employee (ID:%d) already exists.", emp.getEmpID()));
        database.put(emp.getEmpID(), emp);
    }

    @Override
    public void save(int id, Receipt receipt) throws Exception {
        if(!database.containsKey(id))
            throw new Exception("This employee doesn't exist.");
        if(!(database.get(id).getPaymentClassification() instanceof CommissionClassification))
            throw new Exception("You can't post receipt for this type of employee.");
        ((CommissionClassification) database.get(id).getPaymentClassification()).addReceipt(receipt);
    }

    @Override
    public void save(int id, TimeCard timeCard) throws Exception {
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
    public Employee getData(int id) throws Exception {
        if(!database.containsKey(id)) throw new Exception("This employee does not exist.");
        return (Employee) database.get(id);
    }

    @Override
    public boolean dataExists(int id) throws Exception {
        return database.containsKey(id);
    }

    @Override
    public void replace(Employee e) throws Exception {
        if(!database.containsKey(e.getEmpID()))
            throw new Exception("There's no such employee to replace.");
        this.database.replace(e.getEmpID(), e);
    }

    public HashMap<Integer, Employee> getDatabase() {
        return this.database;
    }
}
