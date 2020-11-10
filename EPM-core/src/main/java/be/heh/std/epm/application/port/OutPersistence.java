package be.heh.std.epm.application.port;

import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void save(Employee emp);
    void delete(int id);
    Employee getData(int id);
}
