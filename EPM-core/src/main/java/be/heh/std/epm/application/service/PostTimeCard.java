package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.LocalDateDeserializer;
import be.heh.std.epm.domain.LocalDateSerializer;
import be.heh.std.epm.domain.TimeCard;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class PostTimeCard implements WriteOperation {

    @NotNull @Positive
    int id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull @PastOrPresent
    LocalDate date;
    @NotNull @Positive
    double hours;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.saveTimeCard(id, new TimeCard(date, hours));
    }
}
