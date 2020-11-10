package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

import java.util.ArrayList;

public class PersistenceAdapter implements OutPersistence {

    //"database" for tests
    private ArrayList<Employee> database = new ArrayList();

    @Override
    public void save(Employee emp) {
        boolean valid = true;
        for(Employee e : database) {
            if(emp.getEmpID() == e.getEmpID()) valid = false;
        }
        if(valid) database.add(emp);
    }

    @Override
    public void delete(int id) {
        for(Employee e : database) {
            if (e.getEmpID() == id) {
                database.remove(e);
                break;
            }
        }
    }

    @Override
    public Employee getData(int id) {
        for(Employee e : database) {
            if(e.getEmpID() == id) return e;
        }
        return null;
    }

    public ArrayList<Employee> getDatabase() {
        return this.database;
    }
}
