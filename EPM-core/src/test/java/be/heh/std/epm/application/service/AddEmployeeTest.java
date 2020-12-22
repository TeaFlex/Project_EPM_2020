package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddEmployeeTest {

    private OutPersistence db;
    private AddEmployee add;
    private int id;

    @Before
    public void setUp(){
        db = new ListPersistence();
        id = 11;
        //date = LocalDate.of(2000,12,12);
    }

    public void setBasicInfos() {
        add.setId(id);
        add.setName("Mario");
        add.setAddress("Non");
        add.setEmail("vous.voyez@ce.que.je.veux.dire");
    }

    @Test
    public void addingSalariedEmp() throws Exception{
        add = new AddSalariedEmployee();
        ((AddSalariedEmployee)add).setSalary(1234);
        setBasicInfos();
        add.execute(db);

        Employee dbEmployee = db.getData(id);
        double dbInfos = ((SalariedClassification) dbEmployee.getPaymentClassification()).getSalary();

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentClassification() instanceof SalariedClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof MonthlyPaymentSchedule);
        assertTrue(dbEmployee.getPaymentMethod() instanceof MailMethod);
        assertEquals(1234, dbInfos, 0.0);
    }

    @Test
    public void addingHourlyEmp() throws Exception{
        add = new AddHourlyEmployee();
        ((AddHourlyEmployee)add).setRate(123);
        setBasicInfos();
        add.execute(db);

        Employee dbEmployee = db.getData(id);
        double dbInfos = ((HourlyClassification) dbEmployee.getPaymentClassification()).getSalary();

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentClassification() instanceof HourlyClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
        assertTrue(dbEmployee.getPaymentMethod() instanceof MailMethod);
        assertEquals(123, dbInfos, 0.0);
    }

    @Test
    public void addingCommissionEmp() throws Exception{
        add = new AddCommissionEmployee();
        ((AddCommissionEmployee)add).setSalary(1234);
        ((AddCommissionEmployee)add).setCommissionRate(0.5);
        setBasicInfos();
        add.execute(db);

        Employee dbEmployee = db.getData(id);
        double[] dbInfos = {((CommissionClassification) dbEmployee.getPaymentClassification()).getSalary(),
                ((CommissionClassification) dbEmployee.getPaymentClassification()).getCommissionRate()};

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentClassification() instanceof CommissionClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof BiweeklyPaymentSchedule);
        assertTrue(dbEmployee.getPaymentMethod() instanceof MailMethod);
        assertEquals(1234, dbInfos[0], 0.0);
        assertEquals(0.5, dbInfos[1], 0.0);
    }

    @Test(expected = Exception.class)
    public void doubleAdd() throws Exception {
        //Try to delete the same employee
        setBasicInfos();
        add = new AddSalariedEmployee();
        add.execute(db);
        add.execute(db);
    }
}
