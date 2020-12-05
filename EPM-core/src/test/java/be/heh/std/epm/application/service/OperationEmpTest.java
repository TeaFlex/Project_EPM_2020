package be.heh.std.epm.application.service;

import be.heh.std.epm.application.adapter.ListPersistence;
import be.heh.std.epm.application.data.*;
import be.heh.std.epm.domain.*;
import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class OperationEmpTest {

    private ListPersistence db;
    private DataEmployee emp;
    private OperationEmp op;
    private DataReceipt r;
    private DataTimeCard tc;
    private LocalDate d;

    public void setInfos(DataEmployee e) {
        e.setId(1);
        e.setName("Robert Birquot");
        e.setAddress("Mons");
        e.setEmail("robert.birquot@gmail.com");
    }

    @Before
    public void setUp(){
        db = new ListPersistence();
        op = new OperationEmp(db);
        d = LocalDate.of(2000,12,12);
        r = new DataReceipt();
        tc = new DataTimeCard();
        r.setPrice(200);
        r.setDate(d);
        tc.setDate(d);
        tc.setHours(20);
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
        assertSame("Robert Birquot", dbemp.getName());
        assertSame("robert.birquot@gmail.com", ((MailMethod) dbemp.getPaymentMethod()).getEmail());
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
        op.postTimeCard(emp.getId(), tc);
        TimeCard t = new TimeCard(d, 20);

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

        op.postTimeCard(emp.getId(), tc);
    }

    @Test(expected = Exception.class)
    public void existantPostTimeCard() throws Exception {

        emp = new DataCommissionEmployee();
        setInfos(emp);

        op.postTimeCard(emp.getId(), tc);
        op.postTimeCard(emp.getId(), tc);
    }

    @Test
    public void postReceipt() throws Exception{
        emp = new DataCommissionEmployee();
        setInfos(emp);

        op.addEmployee(emp);
        op.postSaleReceipt(emp.getId(), r);

        Employee res = db.getData(emp.getId());
        Receipt r = new Receipt(d, 200);

        assertEquals(1,((CommissionClassification)res.getPaymentClassification()).getReceipts().size());
        assertNotNull(((CommissionClassification)res.getPaymentClassification()).getReceipts().get(0));
        assertEquals(r, ((CommissionClassification)res.getPaymentClassification()).getReceipts().get(0));
    }

    @Test(expected = Exception.class)
    public void badDPostReceipt() throws Exception {

        emp = new DataSalariedEmployee();
        setInfos(emp);

        op.postSaleReceipt(emp.getId(), r);
    }

    @Test(expected = Exception.class)
    public void existantReceipt() throws Exception {

        emp = new DataCommissionEmployee();
        setInfos(emp);

        op.postSaleReceipt(emp.getId(), r);
        op.postSaleReceipt(emp.getId(), r);
    }
}
