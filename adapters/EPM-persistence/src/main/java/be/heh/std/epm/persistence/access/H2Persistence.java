package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.persistence.access.DBPersistence;

public class H2Persistence extends DBPersistence {

    public H2Persistence (String server, String username, String password) {
        super("h2", server, username, password);
    }

    @Override
    public void save(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public void delete(int id) throws Exception {
        //TODO
    }

    @Override
    public void replace(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public Employee getData(int id) throws Exception {
        //TODO
        return null;
    }
}
