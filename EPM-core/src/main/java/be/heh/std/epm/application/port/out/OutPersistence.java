package be.heh.std.epm.application.port.out;

import be.heh.std.epm.domain.Employee;

public interface OutPersistence {
    void save(Employee emp) throws Exception;
    void delete(int id) throws Exception;
    void replace(Employee emp) throws Exception;
    Employee getData(int id) throws Exception;
    void connect(String username, String password) throws Exception;
    void disconnect() throws Exception;
    boolean isConnected();
}
