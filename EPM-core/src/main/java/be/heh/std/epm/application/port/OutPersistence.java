package be.heh.std.epm.application.port;

import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void save(Employee emp);
    Employee getData(int id);
}
