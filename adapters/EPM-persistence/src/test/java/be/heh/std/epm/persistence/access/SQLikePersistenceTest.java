package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataHourlyEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SQLikePersistenceTest {

    private OutPersistence db;
    private DataEmployee employee;

    @Before
    public void setup() {
        db = new H2Persistence("file:~/h2DBs/mydb", "user","123");
        employee = new DataHourlyEmployee();
        ((DataHourlyEmployee) employee).setSalary(1200);
    }

    public void setInfos(DataEmployee e) {
        e.setId(34);
        e.setName("aaaaaa");
        e.setAddress("aaaaaaa");
    }

    public void setBankMethod(DataEmployee e) {
        setInfos(e);
        e.setBank("aaaaaaaaaaaa");
        e.setIban("AA4324234234243255");
    }

    public void setMailMethod(DataEmployee e) {
        setInfos(e);
        e.setEmail("aaaaaaaa@gmail.com");
    }

    @Test
    public void saveEmployee() throws Exception{
        setMailMethod(employee);
        db.save(employee.toEmployee());
        assertTrue(db.dataExists(employee.getId()));
        db.delete(employee.getId());
    }
}