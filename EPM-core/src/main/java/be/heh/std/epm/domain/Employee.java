package be.heh.std.epm.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class Employee {

    @Getter
    private int empID;
    @Getter
    private String name;
    @Getter
    private String address;
    @Getter @Setter
    private PaymentClassification paymentClassification;
    @Getter @Setter
    private PaymentMethod paymentMethod;
    @Getter @Setter
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
