package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.service.Operation;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLikePersistenceTest {

    private OutPersistence db;
    private Employee employee;

    @Before
    public void setup() {
        try{
            db = new H2Persistence();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        employee = new Employee(1, "test", "test");
    }

    public void setBankMethod() {
        employee.setPaymentMethod(new DirectDepositMethod("Belfius", "BE23455677788"));
    }

    public void setMailMethod() {
        employee.setPaymentMethod(new MailMethod("test@test.test"));
    }

    public void setBasicInfos() {
        employee.setPaymentClassification(new SalariedClassification(1234));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
    }

    @Test
    public void saveEmployee() throws Exception{
        setBasicInfos();
        setBankMethod();
        db.saveEmployee(employee);
        assertTrue(db.dataExists(employee.getEmpID()));
        assertEquals(employee, db.getData(employee.getEmpID()));
    }

    @Test(expected = Exception.class)
    public void badSaveEmployee() throws Exception {
        setBankMethod();
        db.saveEmployee(employee);
        db.saveEmployee(employee);
    }
}