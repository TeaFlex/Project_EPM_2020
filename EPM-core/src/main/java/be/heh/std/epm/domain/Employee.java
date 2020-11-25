package be.heh.std.epm.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class Employee {

    private int empID;
    private String name;
    private String address;
    private PaymentClassification paymentClassification;
    private PaymentMethod paymentMethod;
    private PaymentSchedule paymentSchedule;

    public Employee(int empID, String name, String address) {
        this.empID = empID;
        this.name = name;
        this.address = address;
    }

    public void payDay(PayCheck payCheck) {
        DateRange dateRange = paymentSchedule.getDateRange(payCheck.getDate());
        payCheck.setPay(paymentClassification.getPay(dateRange));
    }

    public boolean isValidPayDate(LocalDate date) {
        return paymentSchedule.isValidPayDate(date);
    }
}
