package be.heh.std.epm.application.port.out;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.Receipt;
import be.heh.std.epm.domain.TimeCard;

public interface OutPersistence {
    void save(Employee emp) throws Exception;
    void save(int id, Receipt receipt) throws Exception;
    void save(int id, TimeCard timeCard) throws Exception;
    void delete(int id) throws Exception;
    void replace(Employee emp) throws Exception;
    Employee getData(int id) throws Exception;
    boolean dataExists(int id) throws Exception;
}
