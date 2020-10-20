package be.heh.std.epm.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Employee {

    private int empID;
    private String name;
    private String address;
    @Getter @Setter private PaymentClassification paymentClassification;
    @Getter @Setter private PaymentMethod paymentMethod;
    @Getter @Setter private PaymentSchedule paymentSchedule;

    public Employee(int empID, String name, String address) {
        this.empID = empID;
        this.name = name;
        this.address = address;
    }

    public void payDay(PayCheck payCheck) {
        payCheck.setSalary(paymentClassification.getSalary());
    }

    public boolean isValidPayDate(LocalDate date) {
        return paymentSchedule.isValidPayDate(date);
    }
}
