package be.heh.std.epm.application.service;

import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.application.port.out.OutPersistence;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.*;

public class DeleteEmployeeTest {

    private OutPersistence db;
    private DeleteEmployee del;
    private AddEmployee add;

    @Before
    public void setUp() {
        db = new ListPersistence();
        add = new AddSalariedEmployee();
    }

    public void setBasicInfos() throws Exception {
        add.setId(1);
        add.setName("Luigi");
        add.setAddress("Pasta");
        add.setEmail("mama@mi.a");
        add.execute(db);
    }

    @Test
    public void deletingEmps() throws Exception {
        setBasicInfos();
        del = new DeleteEmployee();
        del.setId(1);
        del.execute(db);
        assertFalse(db.dataExists(1));
    }

    @Test(expected = Exception.class)
    public void badDelete() throws Exception {
        //Tentative de suppression d'un employé inexistant
        del.setId(1);
        del.execute(db);
    }
}
