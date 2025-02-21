package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateName extends UpdateEmployee {

    @NotEmpty
    String newName;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.updateName(id, newName);
    }
}
