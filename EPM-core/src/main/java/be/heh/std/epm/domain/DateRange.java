package be.heh.std.epm.domain;

import lombok.Getter;

import java.time.LocalDate;

public class DateRange {

    @Getter private LocalDate start;
    @Getter private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithinRange(LocalDate date) {
        return (date.isAfter(start) || date.equals(start)) && (date.isBefore(end) || date.equals(end));
    }
}
