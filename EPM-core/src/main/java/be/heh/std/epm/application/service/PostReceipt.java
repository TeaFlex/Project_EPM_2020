package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.LocalDateDeserializer;
import be.heh.std.epm.domain.LocalDateSerializer;
import be.heh.std.epm.domain.Receipt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class PostReceipt implements WriteOperation {

    @NotNull
    int id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull @PastOrPresent
    LocalDate date;
    @NotNull
    double price;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.saveReceipt(id, new Receipt(date, price));
    }
}
