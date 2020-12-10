package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.ListPersistence;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class AddEmployeeTest {

    private OutPersistence db;
    private Employee emp;
    private AddEmployee add;
    private LocalDate date;

    @Before
    public void setUp(){
        db = new ListPersistence();
        date = LocalDate.of(2000,12,12);
    }

    public void setBasicInfos() {
        add.setId(1);
        add.setName("aaaaa");
        add.setAddress("aaaaa");
        add.setEmail("aaaaa@aaaaa.a");
    }

    @Test
    public void addingEmps() throws Exception{
        add = new AddCommissionEmployee();
        setBasicInfos();
        add.execute(db);
        assertTrue(db.dataExists(1));
    }

    @Test(expected = Exception.class)
    public void badAdd() throws Exception {
        //Try to delete the same employee
        setBasicInfos();
        add = new AddSalariedEmployee();
        add.execute(db);
        add.execute(db);
    }
}
