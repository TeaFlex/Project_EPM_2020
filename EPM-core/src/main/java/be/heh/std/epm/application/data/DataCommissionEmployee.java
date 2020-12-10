package be.heh.std.epm.application.data;


import be.heh.std.epm.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data @EqualsAndHashCode(callSuper = false)
public class DataCommissionEmployee extends DataEmployee {

    @NotNull
    private int salary;
    @NotNull
    private float commissionRate;

    public DataCommissionEmployee(Employee employee) {
        super(employee);
        if (!(employee.getPaymentClassification() instanceof CommissionClassification && employee.getPaymentSchedule() instanceof BiweeklyPaymentSchedule)) {
            throw new IllegalArgumentException();
        }
    }

    public DataCommissionEmployee() {

    }

    @Override
    public Employee toEmployee() throws Exception {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new BiweeklyPaymentSchedule());
        employee.setPaymentClassification(new CommissionClassification(salary, commissionRate));
        return employee;
    }
}
