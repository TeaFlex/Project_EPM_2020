package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddCommissionEmployee extends AddEmployee {

    @NotNull
    double salary;
    @NotNull
    double commissionRate;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        Employee e = new Employee(id, name, address);
        e.setPaymentMethod(getPayementMethod());
        e.setPaymentSchedule(new BiweeklyPaymentSchedule());
        e.setPaymentClassification(new CommissionClassification(salary, commissionRate));
        outPersistence.saveEmployee(e);
    }
}
