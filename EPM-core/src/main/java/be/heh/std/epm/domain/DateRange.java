package be.heh.std.epm.domain;

import java.time.LocalDate;

public class DateRange {

    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) throws IllegalArgumentException {
        if (start.isAfter(end)) throw new IllegalArgumentException("The start date must precede the end date.");
        this.start = start;
        this.end = end;
    }

    public boolean isWithinRange(LocalDate date) {
        return (date.isAfter(start) || date.equals(start)) && (date.isBefore(end) || date.equals(end));
    }
}
