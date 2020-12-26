package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateToSalaried extends UpdateEmployee {

    @NotNull @Positive
    double salary;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.updateToSalaried(id, salary);
    }
}
