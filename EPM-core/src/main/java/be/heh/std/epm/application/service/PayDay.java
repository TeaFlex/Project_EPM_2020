package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.PayCheck;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PayDay {

    @NotNull
    int id;

    public PayCheck payday(OutPersistence outPersistence) throws Exception {
        Employee employee = outPersistence.getData(id);
        PayCheck payCheck = new PayCheck(LocalDate.now());
        employee.payDay(payCheck);
        return payCheck;
    }
}
