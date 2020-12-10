package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data @EqualsAndHashCode(callSuper = false)
public class DataSalariedEmployee extends DataEmployee {

    @NotNull
    int salary;

    public DataSalariedEmployee(Employee employee) {
        super(employee);
        if (!(employee.getPaymentClassification() instanceof SalariedClassification && employee.getPaymentSchedule() instanceof MonthlyPaymentSchedule)) {
            throw new IllegalArgumentException();
        }

    }

    public DataSalariedEmployee() {

    }

    @Override
    public Employee toEmployee() throws Exception {
        Employee employee = super.toEmployee();
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());
        employee.setPaymentClassification(new SalariedClassification(salary));
        return employee;
    }
}
