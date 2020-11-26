package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.TimeCard;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DataTimeCard {
    @NotNull @NotEmpty
    String date;
    @NotNull
    double hours;

    public TimeCard toTimeCard() {
        return new TimeCard(LocalDate.parse(date), hours);
    }
}
