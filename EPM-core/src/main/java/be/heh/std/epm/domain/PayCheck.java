package be.heh.std.epm.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PayCheck {

    @Getter
    private LocalDate date;
    @Getter @Setter
    private double pay;
    private Map<String, String> fields = new HashMap<>();

    public PayCheck(LocalDate date) {
        this.date = date;
    }

    public void setField(String field, String value) {
        fields.put(field, value);
    }

    public String getField(String field) {
        return fields.get(field);
    }
}
