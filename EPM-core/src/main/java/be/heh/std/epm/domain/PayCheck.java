package be.heh.std.epm.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PayCheck {

    @Getter private LocalDate date;
    @Getter @Setter
    private double salary;

    public PayCheck(LocalDate date) {
        this.date = date;
    }
}
