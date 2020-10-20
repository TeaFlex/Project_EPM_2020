package be.heh.std.epm.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee employee;
    private PayCheck pc;

    @Before
    public void setUp() throws Exception {
        employee = new Employee(100, "toto", "av maistriau");
        LocalDate payDate = LocalDate.of(2019, 10, 2);
        pc = new PayCheck(payDate);
    }

    @Test
    public void createSalariedEmployee() {

        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());

        employee.payDay(pc);
        double pay = pc.getSalary();
        assertEquals(1000.0, pay, 0.01);

        PaymentSchedule ps = employee.getPaymentSchedule();
        assertTrue(ps instanceof MonthlyPaymentSchedule);

        PaymentMethod pm = employee.getPaymentMethod();
        assertEquals("direct deposit into ING : be80-4444-444", pm.toString());

    }

    @Test
    public void createHourlyEmployee() {

        employee.setPaymentClassification(new HourlyClassification(20.0));
        employee.setPaymentMethod(new MailMethod("toto@gmail.com"));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());

        LocalDate date = LocalDate.of(2019, 10, 1);
        LocalDate nextDate = LocalDate.of(2019, 10, 2);
        LocalDate dateOutside = LocalDate.of(2019, 9, 2);

        PaymentClassification classification = employee.getPaymentClassification();
        ((HourlyClassification) classification).addTimeCard(new TimeCard(date, 8.0));
        ((HourlyClassification) classification).addTimeCard(new TimeCard(nextDate, 10.0));
        ((HourlyClassification) classification).addTimeCard(new TimeCard(dateOutside, 8.0));

        employee.payDay(pc);
        double pay = pc.getSalary();

        assertEquals(380.0, pay, 0.01);

        PaymentSchedule ps = employee.getPaymentSchedule();
        assertTrue(ps instanceof WeeklyPaymentSchedule);

        PaymentMethod pm = employee.getPaymentMethod();
        assertEquals("mail : toto@gmail.com", pm.toString());
    }

    @Test
    public void monthlyPaymentSchedule() {
        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());

        LocalDate LastDayOfMonth = LocalDate.of(2019, 10, 31);

        assertTrue(employee.isDatePay(LastDayOfMonth));
    }

    @Test
    public void monthlyPaymentScheduleWrong() {
        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());

        LocalDate firstDayOfMonthWrong = LocalDate.of(2019, 10, 1);

        assertFalse(employee.isDatePay(firstDayOfMonthWrong));
    }

    @Test
    public void weeklyPaymentSchedule() {
        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());

        LocalDate fridayDate = LocalDate.of(2020, 10, 2);

        assertTrue(employee.isDatePay(fridayDate));
    }

    @Test
    public void weeklyPaymentScheduleWrong() {
        employee.setPaymentClassification(new SalariedClassification(1000));
        employee.setPaymentMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());

        LocalDate MondayDate = LocalDate.of(2020, 10, 5);

        assertFalse(employee.isDatePay(MondayDate));
    }
}