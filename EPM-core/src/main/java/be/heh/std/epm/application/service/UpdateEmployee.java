package be.heh.std.epm.application.service;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public abstract class UpdateEmployee implements WriteOperation {

    @NotNull
    int id;

}
