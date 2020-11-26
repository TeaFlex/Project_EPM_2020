package be.heh.std.epm.persistence;

import be.heh.std.epm.domain.Employee;
import java.sql.*;

public class H2Persistence extends DBPersistence {

    public H2Persistence (String server) {
        super("h2", server);
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
