package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataSalariedEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;
import org.junit.Before;
import org.junit.Test;

public class DBPersistenceTest {

    private OutPersistence db;
    private DataEmployee employee;

    @Before
    public void setup() {
        db = new H2Persistence("file:~/h2DBs/mydb", "user","123");
        employee = new DataSalariedEmployee();
    }

    public void setInfos(DataEmployee e) {
        e.setId(33);
        e.setName("Joseph Garcia");
        e.setAddress("Pakistan");
    }

    public void setBankMethod(DataEmployee e) {
        setInfos(e);
        e.setBank("Western Union");
        e.setIban("WE4324234234243255");
    }

    public void setMailMethod(DataEmployee e) {
        setInfos(e);
        e.setEmail("joseph.garcia@gmail.com");
    }

    @Test
    public void saveEmployee() throws Exception{
        setBankMethod(employee);
        //db.save(employee.toEmployee());

        System.out.println(db.getData(employee.getId()));
    }
}