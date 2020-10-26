package be.heh.std.epm.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class TimeCard {
    LocalDate date;
    double hours;
}
