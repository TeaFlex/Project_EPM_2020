package be.heh.std.epm.application.port.out;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void save(Employee emp);
    void delete(int id);
    void replace(Employee emp);
    Employee getData(int id);
}
