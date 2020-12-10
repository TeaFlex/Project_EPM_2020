package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.ListPersistence;
import be.heh.std.epm.application.port.out.OutPersistence;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.*;

public class DeleteEmployeeTest {

    private OutPersistence db;
    private DeleteEmployee del;
    private AddEmployee add;
    private LocalDate date;

    @Before
    public void setUp(){
        db = new ListPersistence();
        date = LocalDate.of(2000,12,12);
        add = new AddSalariedEmployee();
    }

    public void setBasicInfos() throws Exception{
        add.setId(1);
        add.setName("aaaaa");
        add.setAddress("aaaaa");
        add.setEmail("aaaaa@aaaaa.a");
        add.execute(db);
    }

    @Test
    public void deletingEmps() throws Exception{
        setBasicInfos();
        del = new DeleteEmployee();
        del.setId(1);
        del.execute(db);
        assertFalse(db.dataExists(1));
    }

    @Test(expected = Exception.class)
    public void badDelete() throws Exception {
        //Try to Delete non existant Employee
        del.setId(1);
        del.execute(db);
    }
}
