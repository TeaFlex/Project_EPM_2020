package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.MonthlyPaymentSchedule;
import be.heh.std.epm.domain.SalariedClassification;

public class DataSalariedEmployee extends DataEmployee{
    public DataSalariedEmployee(int id, String name, String address, double salary) {
        super(id, name, address);
        this.paymentClassification = new SalariedClassification(salary);
        this.paymentSchedule = new MonthlyPaymentSchedule();
    }
}
