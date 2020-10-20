package be.heh.std.epm.domain;

import lombok.Getter;

import java.time.LocalDate;

public class TimeCard {

    @Getter private LocalDate date;
    @Getter private double hours;

    public TimeCard(LocalDate date, double hours) {
        this.date = date;
        this.hours = hours;
    }
}
