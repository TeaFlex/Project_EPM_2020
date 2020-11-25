package be.heh.std.epm.persistence;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;

public class H2Persistence implements OutPersistence {
    @Override
    public void save(Employee emp) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void replace(Employee emp) throws Exception {

    }

    @Override
    public Employee getData(int id) throws Exception {
        return null;
    }
}
