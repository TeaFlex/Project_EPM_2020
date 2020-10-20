package be.heh.std.epm.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PayCheck {

    @Getter private LocalDate date;
    @Getter private double salary;
    private Map<String, String> fields = new HashMap<>();

    public PayCheck(LocalDate date) {
        this.date = date;
    }

    public void setPay(double salary) {
        this.salary = salary;
    }

    public void setField(String field, String value) {
        fields.put(field, value);
    }

    public String getField(String field) {
        return fields.get(field);
    }

}
