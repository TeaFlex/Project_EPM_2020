package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.CommissionClassification;

public class AddCommissionEmp extends AddEmp {

    public AddCommissionEmp(int id, String name, String address, double mtlySlry, double comRate, OutPersistence output) {
        super(id, name, address, output);
        this.emp.setPaymentClassification(new CommissionClassification(mtlySlry, comRate));
        this.output.writeInDB(this.emp);
    }
}
