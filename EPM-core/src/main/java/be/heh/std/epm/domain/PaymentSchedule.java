package be.heh.std.epm.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public interface PaymentSchedule {
    boolean isValidPayDate(LocalDate date);
    DateRange getDateRange(LocalDate date);
}
