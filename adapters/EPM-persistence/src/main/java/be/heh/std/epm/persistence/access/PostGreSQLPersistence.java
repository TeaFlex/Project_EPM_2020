package be.heh.std.epm.persistence.access;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.Receipt;
import be.heh.std.epm.domain.TimeCard;

public class PostGreSQLPersistence extends SQLikePersistence {

    public PostGreSQLPersistence(String server, String username, String password) {
        super("sql", "://", username, password);
    }

    @Override
    public void saveEmployee(Employee emp) throws Exception {
        //TODO
    }

    @Override
    public void saveReceipt(int id, Receipt receipt) throws Exception {

    }

    @Override
    public void saveTimeCard(int id, TimeCard timeCard) throws Exception {

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

    @Override
    public boolean dataExists(int id) throws Exception {
        return false;
    }
}
