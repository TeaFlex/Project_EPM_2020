package be.heh.std.epm.domain;

import lombok.Getter;

public class SalariedClassification implements PaymentClassification {

    @Getter
    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    @Override
    public double getPay(DateRange dateRange) {
        return this.salary;
    }
}
