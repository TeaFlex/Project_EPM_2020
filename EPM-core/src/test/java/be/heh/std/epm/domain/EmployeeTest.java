package be.heh.std.epm.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class EmployeeTest {
    private Employee employee;

    @Before
    public void setUp() throws Exception {
        employee = new Employee(100, "toto", "av maistriau");
    }

    @Test
    public void createSalariedEmployee() {

        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new MonthlyPaymentySchedule());

        assertEquals(1000.0, employee.calculatePay(), 0.01);

        PaymentSchedule ps = employee.getPaymentSchedule();
        assertTrue(ps instanceof MonthlyPaymentySchedule);

        PaymentMethod pm = employee.getPaymentMethod();
        assertEquals("direct deposit into ING : be80-4444-444", pm.toString());

    }

    @Test
    public void createHourlyEmployee() {

        employee.setPaymentClassification(new HourlyClassification(20.0));
        employee.setPaymentMethod(new MailMethod("toto@gmail.com"));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());

        Calendar date = new GregorianCalendar(2019, Calendar.NOVEMBER, 1);
        Calendar nextDate = new GregorianCalendar(2019, Calendar.NOVEMBER, 2);
        PaymentClassification classification = employee.getPaymentClassification();
        ((HourlyClassification) classification).addTimeCard(new TimeCard(date, 8.0));
        ((HourlyClassification) classification).addTimeCard(new TimeCard(nextDate, 10.0));

        assertEquals(380.0, employee.calculatePay(), 0.01);

        PaymentSchedule ps = employee.getPaymentSchedule();
        assertTrue(ps instanceof WeeklyPaymentSchedule);

        PaymentMethod pm = employee.getPaymentMethod();
        assertEquals("mail : toto@gmail.com", pm.toString());

    }
}