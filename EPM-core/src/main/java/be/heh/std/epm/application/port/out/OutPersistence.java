package be.heh.std.epm.application.port.out;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void save(DataEmployee emp);
    void delete(int id);
    DataEmployee getData(int id);
}
