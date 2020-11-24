package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.MonthlyPaymentSchedule;
import be.heh.std.epm.domain.SalariedClassification;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class DataSalariedEmployee extends DataEmployee {

    @Getter @Setter @NotNull
    int salary;

    @Override
    public Employee toEmployee() {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());
        employee.setPaymentClassification(new SalariedClassification(salary));
        return employee;
    }
}
