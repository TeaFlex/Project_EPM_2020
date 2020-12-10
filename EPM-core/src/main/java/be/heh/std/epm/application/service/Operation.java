package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;

public interface Operation {
    void execute(OutPersistence outPersistence) throws Exception;
}
