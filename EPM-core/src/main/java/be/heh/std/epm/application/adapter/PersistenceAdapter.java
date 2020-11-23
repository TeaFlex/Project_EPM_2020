package be.heh.std.epm.application.adapter;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;

import java.util.ArrayList;

public class PersistenceAdapter implements OutPersistence {

    //"database" for tests
    private ArrayList<DataEmployee> database = new ArrayList();

    @Override
    public void save(DataEmployee emp) {
        boolean valid = true;
        for(DataEmployee e : database) {
            if(emp.getId() == e.getId()) valid = false;
        }
        if(valid) database.add(emp);
    }

    @Override
    public void delete(int id) {
        for(DataEmployee e : database) {
            if (e.getId() == id) {
                database.remove(e);
                break;
            }
        }
    }

    @Override
    public DataEmployee getData(int id) {
        for(DataEmployee e : database) {
            if(e.getId() == id) return e;
        }
        return null;
    }

    public ArrayList<DataEmployee> getDatabase() {
        return this.database;
    }
}
