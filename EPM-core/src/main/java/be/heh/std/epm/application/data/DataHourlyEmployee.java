package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.HourlyClassification;
import be.heh.std.epm.domain.WeeklyPaymentSchedule;

public class DataHourlyEmployee extends DataEmployee{
    public DataHourlyEmployee(int id, String name, String address, double hourlyRate) {
        super(id, name, address);
        this.paymentClassification = new HourlyClassification(hourlyRate);
        this.paymentSchedule = new WeeklyPaymentSchedule();
    }
}
