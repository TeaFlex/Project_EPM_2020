package be.heh.std.epm.domain;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class SalariedClassification implements PaymentClassification {

    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    @Override
    public double getPay(DateRange dateRange) {
        return this.salary;
    }
}
