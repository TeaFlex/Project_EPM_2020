package be.heh.std.epm.application.service;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ValidPaymentMethod
public abstract class AddEmployee implements Operation {

    @NotNull
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

}
