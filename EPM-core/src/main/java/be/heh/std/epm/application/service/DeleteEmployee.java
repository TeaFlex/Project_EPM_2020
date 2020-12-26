package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteEmployee implements WriteOperation {

    @NotNull
    int id;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        outPersistence.deleteEmployee(id);
    }
}
