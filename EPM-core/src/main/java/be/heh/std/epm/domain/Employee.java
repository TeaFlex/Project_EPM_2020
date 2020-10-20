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

    public double calculatePay() {

        return this.paymentClassification.getSalary(); // Attention ! C'est presque la bonne réponse.
    }

    public void payDay(PayCheck payCheck) {

    }

    public boolean isDatePay(LocalDate date) {
        return false;
    }
}
