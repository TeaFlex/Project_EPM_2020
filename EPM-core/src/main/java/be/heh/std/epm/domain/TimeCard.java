package be.heh.std.epm.domain;

import java.util.Calendar;

public class TimeCard {

    private Calendar date;
    private double hours;

    public TimeCard(Calendar date, double hours) {
        this.date = date;
        this.hours = hours;
    }
}
