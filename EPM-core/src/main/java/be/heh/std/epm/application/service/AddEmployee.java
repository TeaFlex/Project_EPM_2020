package be.heh.std.epm.application.service;

import be.heh.std.epm.domain.DirectDepositMethod;
import be.heh.std.epm.domain.MailMethod;
import be.heh.std.epm.domain.PaymentMethod;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
@ValidPaymentMethod
public abstract class AddEmployee implements WriteOperation {

    @NotNull @Positive
    int id;
    @NotEmpty
    String name;
    @NotEmpty
    String address;
    @Email
    String email;
    String bank;
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    String iban;

    protected PaymentMethod getPaymentMethod() throws Exception {
        if (!Objects.isNull(email)) {
            return new MailMethod(email);
        } else if (!Objects.isNull(bank) && !Objects.isNull(iban)) {
            return new DirectDepositMethod(bank, iban);
        } else {
            //Ne devrait jamais se produire grâce à la validation
            throw new Exception("Payment method information is either missing or incomplete.");
        }
    }
}
