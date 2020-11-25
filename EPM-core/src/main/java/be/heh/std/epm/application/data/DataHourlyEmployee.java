package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.HourlyClassification;
import be.heh.std.epm.domain.WeeklyPaymentSchedule;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class DataHourlyEmployee extends DataEmployee {

    @Getter @Setter @NotNull
    private float salary;

    @Override
    public Employee toEmployee() throws Exception {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
        employee.setPaymentClassification(new HourlyClassification(salary));
        return employee;
    }
}
