package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceAdapter implements OutPersistence {

    //"database" for tests
    private HashMap<Integer, Employee> database = new HashMap<>();

    @Override
    public void save(Employee emp) {
        database.putIfAbsent(emp.getEmpID(), emp);
    }

    @Override
    public void delete(int id) {
        database.remove(id);
    }

    @Override
    public Employee getData(int id) {
        return (Employee) database.get(id);
    }

    @Override
    public void replace(Employee e) {
        this.database.replace(e.getEmpID(), e);
    }

    public HashMap<Integer, Employee> getDatabase() {
        return this.database;
    }
}
