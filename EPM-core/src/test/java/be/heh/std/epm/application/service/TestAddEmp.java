package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.PersistenceAdapter;
import be.heh.std.epm.domain.Employee;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class TestAddEmp {

    private AddEmp addition;
    private PersistenceAdapter db;

    @Before
    public void setUp(){
        db = new PersistenceAdapter();
    }

    @Test
    public void addingEmps(){
        addition = new AddSalariedEmp(1, "Larcin Vincent", "Frameries", 1500, db);
        addition = new AddCommissionEmp(2, "Jeuniaux Nicolas", "Quévy-le-petit", 1400, 0.5, db);
        addition = new AddHourlyEmp(3, "Vitali Luca", "Quévy-le-grand", 20, db);

        for (Employee e : db.getDatabase()) {
            System.out.println(e.toString());
        }

        assertEquals(3, db.getDatabase().size());
    }
}
