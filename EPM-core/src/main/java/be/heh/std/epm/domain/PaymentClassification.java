package be.heh.std.epm.domain;

public interface PaymentClassification {
    double getRate();
    double getPay(DateRange dateRange);
}
