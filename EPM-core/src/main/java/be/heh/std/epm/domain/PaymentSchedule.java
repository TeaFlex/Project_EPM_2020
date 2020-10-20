package be.heh.std.epm.domain;

import java.time.LocalDate;

public interface PaymentSchedule {
    boolean isValidPayDate(LocalDate date);
    DateRange getDateRange(LocalDate date);
}
