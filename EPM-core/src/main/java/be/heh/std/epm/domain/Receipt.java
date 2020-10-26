package be.heh.std.epm.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Receipt {
    LocalDate date;
    double price;
}
