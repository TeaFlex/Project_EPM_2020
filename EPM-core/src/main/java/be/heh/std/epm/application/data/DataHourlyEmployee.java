package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data @EqualsAndHashCode(callSuper = false)
public class DataHourlyEmployee extends DataEmployee {

    @NotNull
    private float salary;

    public DataHourlyEmployee(Employee employee) {
        super(employee);
        if (!(employee.getPaymentClassification() instanceof HourlyClassification && employee.getPaymentSchedule() instanceof WeeklyPaymentSchedule)) {
            throw new IllegalArgumentException();
        }
    }

    public DataHourlyEmployee() {

    }

    @Override
    public Employee toEmployee() throws Exception {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
        employee.setPaymentClassification(new HourlyClassification(salary));
        return employee;
    }
}
