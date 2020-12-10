package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.ListPersistence;
import be.heh.std.epm.application.data.*;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;
import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AddEmployeeTest {

    private OutPersistence db;
    private Employee emp;
    private AddEmployee op;
    private LocalDate d;

    @Before
    public void setUp(){
        db = new ListPersistence();
        d = LocalDate.of(2000,12,12);
    }

    @Test
    public void addingEmps() throws Exception{

        op = new AddCommissionEmployee();

    }

    @Test(expected = Exception.class)
    public void badAdd() throws Exception {

    }
}
