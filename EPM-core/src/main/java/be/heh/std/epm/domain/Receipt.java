package be.heh.std.epm.domain;

import lombok.Getter;

import java.time.LocalDate;

public class Receipt {


    @Getter private LocalDate date;
    @Getter private double price;

    public Receipt(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }
}
