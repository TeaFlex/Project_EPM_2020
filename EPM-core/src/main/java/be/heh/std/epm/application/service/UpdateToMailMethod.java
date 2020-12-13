package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateToMailMethod extends UpdateEmployee {

    @NotEmpty @Email
    String email;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.updateToMailMethod(id, email);
    }
}
