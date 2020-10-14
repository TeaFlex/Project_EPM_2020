package be.heh.std.epm.domain;

public class SalariedClassification implements PaymentClassification {

    private int salary;

    public SalariedClassification(int salary) {
        this.salary = salary;
    }
}
