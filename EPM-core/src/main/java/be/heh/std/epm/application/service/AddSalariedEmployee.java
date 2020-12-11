package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.MonthlyPaymentSchedule;
import be.heh.std.epm.domain.SalariedClassification;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddSalariedEmployee extends AddEmployee {

    @NotNull
    double salary;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        Employee employee = new Employee(id, name, address);
        employee.setPaymentMethod(getPaymentMethod());
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());
        employee.setPaymentClassification(new SalariedClassification(salary));
        outPersistence.saveEmployee(employee);
    }
}
