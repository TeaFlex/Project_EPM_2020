package be.heh.std.epm.domain;

import java.time.LocalDate;

public class MonthlyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isValidPayDate(LocalDate date) {
        return date == getLastWorkingDayOfMonth(date);
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
