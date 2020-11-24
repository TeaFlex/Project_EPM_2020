package be.heh.std.epm.application.data;


import be.heh.std.epm.domain.BiweeklyPaymentSchedule;
import be.heh.std.epm.domain.CommissionClassification;

public class DataCommissionEmployee extends DataEmployee {
    public DataCommissionEmployee(int id, String name, String address, double salary, double comRate) {
        super(id, name, address);
        this.paymentClassification = new CommissionClassification(salary, comRate);
        this.paymentSchedule = new BiweeklyPaymentSchedule();
    }
}
