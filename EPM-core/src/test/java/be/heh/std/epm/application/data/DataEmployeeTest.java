package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DataEmployeeTest {

    private DataEmployee emp;

    public void setInfos(DataEmployee e) {
        e.setId(33);
        e.setName("Joseph Garcia");
        e.setAddress("Pakistan");
    }

    public void setBankMethod(DataEmployee e) {
        setInfos(e);
        e.setBank("Western Union");
        e.setIban("PA4324234234243255");
    }

    public void setMailMethod(DataEmployee e) {
        setInfos(e);
        e.setEmail("joseph.garcia@gmail.com");
    }

    @Test
    public void dataEmp() throws Exception{

        //Commission Employee + Mail method
        emp = new DataCommissionEmployee();
        setMailMethod(emp);

        Employee res = emp.toEmployee();
        assertTrue(res.getPaymentMethod() instanceof MailMethod);
        assertTrue(res.getPaymentClassification() instanceof CommissionClassification);
        assertTrue(res.getPaymentSchedule() instanceof BiweeklyPaymentSchedule);

        //Hourly Employee + Direct deposit method
        emp = new DataHourlyEmployee();
        setBankMethod(emp);

        res = emp.toEmployee();
        assertTrue(res.getPaymentMethod() instanceof DirectDepositMethod);
        assertTrue(res.getPaymentClassification() instanceof HourlyClassification);
        assertTrue(res.getPaymentSchedule() instanceof WeeklyPaymentSchedule);

        //Salaried Employee
        emp = new DataSalariedEmployee();
        setBankMethod(emp);

        res = emp.toEmployee();
        assertTrue(res.getPaymentClassification() instanceof SalariedClassification);
        assertTrue(res.getPaymentSchedule() instanceof MonthlyPaymentSchedule);
    }
}