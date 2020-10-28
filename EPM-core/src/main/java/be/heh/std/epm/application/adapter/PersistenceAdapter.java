package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

import java.util.ArrayList;

public class PersistenceAdapter implements OutPersistence {

    private ArrayList<Employee> database = new ArrayList();

    @Override
    public void save(Employee emp) {
        database.add(emp);
    }

    @Override
    public Employee getData(int id) {
        for(Employee e : database){
            if(e.getEmpID() == id) return e;
        }
        return null;
    }

    public ArrayList<Employee> getDatabase() {
        return this.database;
    }
}
