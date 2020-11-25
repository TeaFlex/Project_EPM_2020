package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.TestPersistence;
import be.heh.std.epm.application.data.DataCommissionEmployee;
import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataHourlyEmployee;
import be.heh.std.epm.application.data.DataSalariedEmployee;
import be.heh.std.epm.domain.*;
import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class OperationEmpTest {

    private TestPersistence db;
    private DataEmployee emp;
    private OperationEmp op;

    public void setInfos(DataEmployee e) {
        e.setId(1);
        e.setName("Robert Pâtée");
        e.setAddress("Mons");
        e.setEmail("robert.patee@gmail.com");
    }

    @Before
    public void setUp(){
        db = new TestPersistence();
        op = new OperationEmp(db);
    }

    @Test
    public void addingEmps() throws Exception{

        emp = new DataHourlyEmployee();
        setInfos(emp);
        ((DataHourlyEmployee) emp).setSalary(123);

        op.addEmployee(emp);
        assertEquals(1, db.getDatabase().size());
        Employee dbemp = db.getData(emp.getId());
        assertTrue(dbemp.getPaymentClassification() instanceof HourlyClassification);
        assertTrue(dbemp.getPaymentMethod() instanceof MailMethod);
        assertTrue(dbemp.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
        assertSame("Robert Pâtée", dbemp.getName());
        assertSame("robert.patee@gmail.com", ((MailMethod) dbemp.getPaymentMethod()).getEmail());
    }

    @Test(expected = Exception.class)
    public void badAdd() throws Exception {
        emp = new DataHourlyEmployee();
        setInfos(emp);

        op.addEmployee(emp);
        op.addEmployee(emp);
    }

    @Test
    public void deletingEmps() throws Exception{

        emp = new DataCommissionEmployee();
        setInfos(emp);

        op.addEmployee(emp);
        op.deleteEmployee(emp.getId());
        assertEquals(0, db.getDatabase().size());
    }

    @Test(expected = Exception.class)
    public void badDelete() throws Exception{
        op.deleteEmployee(77);
    }

    @Test
    public void postTimeCards() throws Exception {

        emp = new DataHourlyEmployee();
        setInfos(emp);

        op.addEmployee(emp);
        op.postTimeCard(emp.getId(), LocalDate.of(2000,12,23), 25);
        TimeCard t = new TimeCard(LocalDate.of(2000, 12, 23),25);
        assertEquals(1, ((HourlyClassification)db.getData(emp.getId()).getPaymentClassification())
                .getTimeCards().size());
        assertNotNull(((HourlyClassification) db.getData(emp.getId()).
                getPaymentClassification()).getTimeCards().get(0));
        assertEquals(t,((HourlyClassification)db.getData(emp.getId()).
                getPaymentClassification()).getTimeCards().get(0));
    }

    @Test(expected = Exception.class)
    public void badDPostTimeCard() throws Exception {

        emp = new DataSalariedEmployee();
        setInfos(emp);

        op.postTimeCard(emp.getId(), LocalDate.now(), 44);
    }

    @Test
    public void postReceipt() {
        /*emp = new DataCommissionEmployee(78, "Constentin Druart", "Mons", 1600, 0.2);
        emp.setPaymentMethod(new MailMethod("luca.vitali@std.heh.be"));*/
    }
}
