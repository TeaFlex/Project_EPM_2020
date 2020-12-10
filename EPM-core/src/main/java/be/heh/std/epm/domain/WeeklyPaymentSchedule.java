package be.heh.std.epm.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Value
public class WeeklyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isValidPayDate(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.FRIDAY);
    }

    @Override
    public DateRange getDateRange(LocalDate date) {
        return new DateRange(date.minusDays(6), date);
    }
}
