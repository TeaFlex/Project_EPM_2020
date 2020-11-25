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
    }

    @Before
    public void setUp(){
        db = new TestPersistence();
        op = new OperationEmp(db);
    }

    @Test
    public void addingEmps() {

        emp = new DataHourlyEmployee();
        setInfos(emp);
        ((DataHourlyEmployee) emp).setSalary(123);
        emp.setEmail("robert.patee@gmail.com");

        try {
            op.addEmployee(emp);
            assertEquals(1, db.getDatabase().size());
            Employee dbemp = db.getData(emp.getId());
            assertTrue(dbemp.getPaymentClassification() instanceof HourlyClassification);
            assertTrue(dbemp.getPaymentMethod() instanceof MailMethod);
            assertTrue(dbemp.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
            assertSame("Robert Pâtée", dbemp.getName());
            assertSame("robert.patee@gmail.com", ((MailMethod) dbemp.getPaymentMethod()).getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void badAdd() {
        //emp = new DataHourlyEmployee(1, "Larcin Vincent", "Frameries",123);
        //emp.setPaymentMethod(new MailMethod("vincent.larcin@std.heh.be"));

        Exception exception = assertThrows(Exception.class, () -> {
            op.addEmployee(emp);
            op.addEmployee(emp);
        });

        assertTrue(exception.getMessage().length() != 0);
    }

    @Test
    public void deletingEmps() {

        /*emp = new DataCommissionEmployee(3, "Jeuniaux Nicolas", "Quévy-le-petit", 1000,
                0.3);
        emp.setPaymentMethod(new DirectDepositMethod("Belfius", "BE00000000000000"));*/

        try {
            op.addEmployee(emp);
            op.deleteEmployee(emp.getId());
            assertEquals(0, db.getDatabase().size());
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }

    @Test
    public void badDelete() {
        Exception exception = assertThrows(Exception.class, () -> {
            op.deleteEmployee(77);
        });

        assertTrue(exception.getMessage().length() != 0);
    }

    @Test
    public void postTimeCards() {

        /*emp = new DataHourlyEmployee(78, "Vitali Luca", "Quévy-le-grand", 160);
        emp.setPaymentMethod(new MailMethod("luca.vitali@std.heh.be"));*/

        try{
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
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void badDPostTimeCard() {

        /*emp = new DataSalariedEmployee(78, "Vitali Luca", "Quévy-le-grand", 1600);
        emp.setPaymentMethod(new MailMethod("luca.vitali@std.heh.be"));*/

        Exception exception = assertThrows(Exception.class, () -> {
            op.postTimeCard(emp.getId(), LocalDate.now(), 44);
        });

        assertTrue(exception.getMessage().length() != 0);
    }

    @Test
    public void postReceipt() {
        /*emp = new DataCommissionEmployee(78, "Constentin Druart", "Mons", 1600, 0.2);
        emp.setPaymentMethod(new MailMethod("luca.vitali@std.heh.be"));*/
    }
}
