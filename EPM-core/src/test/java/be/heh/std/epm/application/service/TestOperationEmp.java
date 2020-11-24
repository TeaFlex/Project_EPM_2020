package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.PersistenceAdapter;
import be.heh.std.epm.application.data.DataCommissionEmployee;
import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataHourlyEmployee;
import be.heh.std.epm.domain.*;
import org.junit.*;

import javax.xml.crypto.Data;

import java.sql.Time;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestOperationEmp {

    private PersistenceAdapter db;
    private DataEmployee emp;
    private OperationEmp op;

    @Before
    public void setUp(){
        db = new PersistenceAdapter();
        op = new OperationEmp(db);
    }

    @Test
    public void addingEmps() {

        emp = new DataHourlyEmployee(1, "Larcin Vincent", "Frameries",123);
        emp.setPaymentMethod(new MailMethod("vincent.larcin@std.heh.be"));
        op.addEmployee(emp);

        assertEquals(1, db.getDatabase().size());
        Employee dbemp = db.getData(emp.getId());
        assertTrue(dbemp.getPaymentClassification() instanceof HourlyClassification);
        assertTrue(dbemp.getPaymentMethod() instanceof MailMethod);
        assertTrue(dbemp.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
        assertSame("Larcin Vincent", dbemp.getName());
        assertSame("vincent.larcin@std.heh.be", ((MailMethod) dbemp.getPaymentMethod()).getEmail());
    }

    @Test
    public void deletingEmps() {

        emp = new DataCommissionEmployee(3, "Jeuniaux Nicolas", "Quévy-le-petit", 1000, 0.3);
        emp.setPaymentMethod(new DirectDepositMethod("Belfius", "BE00000000000000"));

        op.deleteEmployee(emp.getId());

        assertEquals(0, db.getDatabase().size());
        assertNull(db.getData(emp.getId()));
    }

    @Test
    public void postTimeCards() {

        emp = new DataHourlyEmployee(78, "Vitali Luca", "Quévy-le-grand", 160);
        emp.setPaymentMethod(new MailMethod("luca.vitali@std.heh.be"));

        op.addEmployee(emp);
        op.postTimeCard(emp.getId(), LocalDate.of(2000,12,23), 25);

        TimeCard t = new TimeCard(LocalDate.of(2000, 12, 23),25);
        assertEquals(1, ((HourlyClassification)db.getData(emp.getId()).getPaymentClassification()).getTimeCards().size());
        assertTrue(((HourlyClassification)db.getData(emp.getId()).getPaymentClassification()).getTimeCards().get(0) instanceof TimeCard);
        assertEquals(t, ((HourlyClassification)db.getData(emp.getId()).getPaymentClassification()).getTimeCards().get(0));
    }

    @Test
    public void postReceipt() {

    }
}
