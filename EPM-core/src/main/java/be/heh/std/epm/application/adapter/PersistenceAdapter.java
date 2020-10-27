package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

import java.util.ArrayList;

public class PersistenceAdapter implements OutPersistence {

    private ArrayList<Employee> database = new ArrayList();

    @Override
    public void writeInDB(Employee emp) {
        database.add(emp);
    }

    public ArrayList<Employee> getDatabase() {
        return this.database;
    }
}
