package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateName extends UpdateEmployee {

    @NotNull
    String newName;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {

    }
}
