package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddCommissionEmployee extends AddEmployee {

    @NotNull @Positive
    double salary;
    @NotNull @Positive
    double commissionRate;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        Employee employee = new Employee(id, name, address);
        employee.setPaymentMethod(getPaymentMethod());
        employee.setPaymentSchedule(new BiweeklyPaymentSchedule());
        employee.setPaymentClassification(new CommissionClassification(salary, commissionRate));
        outPersistence.saveEmployee(employee);
    }
}
