package be.heh.std.epm.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Value
public class BiweeklyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isValidPayDate(LocalDate date) {
        return date.equals(date.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.FRIDAY)))
                || date.equals(date.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.FRIDAY)));
    }

    @Override
    public DateRange getDateRange(LocalDate date) {
        //Si le vendredi d'il y a deux semaines était après le 28 du mois,
        //càd que le mois dernier avait 5 vendredis et qu'on est le deuxième vendredi du mois,
        //il faut prendre en compte les commissions des 3 dernières semaines.
        if (date.minusWeeks(2).getDayOfMonth() > 28) {
            return new DateRange(date.minusDays(20), date);
        }
        return new DateRange(date.minusDays(13), date);
    }
}
