package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.HourlyClassification;
import be.heh.std.epm.domain.WeeklyPaymentSchedule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddHourlyEmployee extends AddEmployee {

    @NotNull
    double rate;

    @Override
    public void execute(OutPersistence outPersistence) throws Exception {
        Employee employee = new Employee(id, name, address);
        employee.setPaymentMethod(getPaymentMethod());
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
        employee.setPaymentClassification(new HourlyClassification(rate));
        outPersistence.saveEmployee(employee);
    }
}
