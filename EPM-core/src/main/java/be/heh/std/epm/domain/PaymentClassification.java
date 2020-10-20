package be.heh.std.epm.domain;

import java.time.LocalDate;

public interface PaymentClassification {
    double getPay(DateRange dateRange);
}
