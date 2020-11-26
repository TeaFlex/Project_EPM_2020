package be.heh.std.epm.persistence;

import be.heh.std.epm.domain.Employee;

import java.sql.SQLException;

public class SQLPersistence extends DBPersistence {

    public SQLPersistence(String server) throws SQLException {
        super("sql", "://");
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
