package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.PersistenceAdapter;
import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.domain.*;
import org.junit.*;

import static org.junit.Assert.*;

public class TestAddEmp {

    private PersistenceAdapter db;
    private DataEmployee emp;

    @Before
    public void setUp(){
        db = new PersistenceAdapter();
        PaymentClassification paycl = new CommissionClassification(1000, 0.2);
        PaymentMethod paymeth = new MailMethod("vincent.larcin@std.heh.be");
        WeeklyPaymentSchedule paysch = new WeeklyPaymentSchedule();
        emp = new DataEmployee(1, "Larcin Vincent", "Frameries", paycl, paymeth, paysch);
    }

    @Test
    public void addingEmps(){

        OperationEmp.addEmp(emp, db);

        assertEquals(1, db.getDatabase().size());
        Employee dbemp = db.getData(emp.getId());
        assertTrue(dbemp instanceof Employee);
        assertTrue(dbemp.getPaymentClassification() instanceof CommissionClassification);
        assertTrue(dbemp.getPaymentMethod() instanceof MailMethod);
        assertTrue(dbemp.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
        assertTrue(dbemp.getName() == "Larcin Vincent");
        assertTrue(((MailMethod) dbemp.getPaymentMethod()).getEmail() == "vincent.larcin@std.heh.be");
    }
}
