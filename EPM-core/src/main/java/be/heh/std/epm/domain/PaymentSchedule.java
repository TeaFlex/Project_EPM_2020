package be.heh.std.epm.domain;

import java.time.LocalDate;

public interface PaymentSchedule {
    public boolean isValidPayDate(LocalDate date);
}
