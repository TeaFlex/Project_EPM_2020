package be.heh.std.epm.application.data;


import be.heh.std.epm.domain.BiweeklyPaymentSchedule;
import be.heh.std.epm.domain.CommissionClassification;
import be.heh.std.epm.domain.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class DataCommissionEmployee extends DataEmployee {

    @Getter @Setter @NotNull
    private int salary;
    @Getter @Setter @NotNull
    private float commissionRate;

    @Override
    public Employee toEmployee() throws Exception {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new BiweeklyPaymentSchedule());
        employee.setPaymentClassification(new CommissionClassification(salary, commissionRate));
        return employee;
    }
}
