package be.heh.std.epm.application.service;

import javax.validation.constraints.NotNull;

public abstract class UpdateEmployee implements Operation {

    @NotNull
    int id;

}
