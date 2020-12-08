package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.Receipt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class DataReceipt {
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull @PastOrPresent
    String date;
    @NotNull
    double price;

    public Receipt toReceipt() {
        return new Receipt(LocalDate.parse(date), price);
    }
}
