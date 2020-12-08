package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.TimeCard;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class DataTimeCard {
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull @PastOrPresent
    String date;
    @NotNull
    double hours;

    public TimeCard toTimeCard() {
        return new TimeCard(LocalDate.parse(date), hours);
    }
}
