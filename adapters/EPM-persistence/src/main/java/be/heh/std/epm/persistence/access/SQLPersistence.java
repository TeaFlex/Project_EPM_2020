package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.persistence.access.DBPersistence;

import java.sql.SQLException;

public class SQLPersistence extends DBPersistence {

    public SQLPersistence(String server, String username, String password) throws SQLException {
        super("sql", "://", username, password);
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
