package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public class TestPersistence implements OutPersistence {

    //"database" for tests
    private HashMap<Integer, Employee> database = new HashMap<>();

    @Override
    public void save(Employee emp) throws Exception {
        if(database.containsKey(emp.getEmpID()))
            throw new Exception(String.format("This employee (ID:%d) already exists.", emp.getEmpID()));
        database.put(emp.getEmpID(), emp);
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
    public void connect(String username, String password) throws Exception{

    }

    @Override
    public void disconnect() throws Exception{

    }

    @Override
    public boolean isConnected() {
        return database != null;
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
