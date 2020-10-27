package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.HourlyClassification;
import be.heh.std.epm.domain.WeeklyPaymentSchedule;

public class AddHourlyEmp extends AddEmp {

    public AddHourlyEmp(int id, String name, String address, double hrlyRate, OutPersistence output) {
        super(id, name, address, output);
        this.emp.setPaymentClassification(new HourlyClassification(hrlyRate));
        this.emp.setPaymentSchedule(new WeeklyPaymentSchedule());
        this.output.writeInDB(this.emp);
    }
}
