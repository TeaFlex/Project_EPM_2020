package be.heh.std.epm.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isValidPayDate(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    public DateRange getDateRange(LocalDate date) {
        return new DateRange(date.minusDays(4), date);
    }
}
