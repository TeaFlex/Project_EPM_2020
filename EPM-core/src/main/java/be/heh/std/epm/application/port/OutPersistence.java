package be.heh.std.epm.application.port;

import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void writeInDB(Employee emp);
}
