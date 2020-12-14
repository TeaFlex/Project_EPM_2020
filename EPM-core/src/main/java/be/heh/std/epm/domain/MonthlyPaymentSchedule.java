package be.heh.std.epm.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class MonthlyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isValidPayDate(LocalDate date) {
        return date.equals(getLastWorkingDayOfMonth(date));
    }

    @Override
    public DateRange getDateRange(LocalDate date) {
        return new DateRange(date.withDayOfMonth(1), date);
    }

    private LocalDate getLastWorkingDayOfMonth(LocalDate date) {
        LocalDate lastDay = date.withDayOfMonth(date.lengthOfMonth());
        switch (lastDay.getDayOfWeek()) {
            case SATURDAY:
                return lastDay.minusDays(1);
            case SUNDAY:
                return lastDay.minusDays(2);
            default:
                return lastDay;
        }
    }
}
