package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateToDirectDepositMethod extends UpdateEmployee {

    @NotEmpty
    String bank;
    @NotEmpty @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    String iban;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {

    }
}
