package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.Receipt;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DataReceipt {
    @NotNull @NotEmpty
    String date;
    @NotNull
    double price;

    public Receipt toReceipt() {
        return new Receipt(LocalDate.parse(date), price);
    }
}
