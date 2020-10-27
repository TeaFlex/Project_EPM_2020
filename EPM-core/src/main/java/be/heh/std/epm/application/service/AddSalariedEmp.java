package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.SalariedClassification;

public class AddSalariedEmp extends AddEmp {

    public AddSalariedEmp(int id, String name, String address, double mtlySlry, OutPersistence output) {
        super(id, name, address, output);
        this.emp.setPaymentClassification(new SalariedClassification(mtlySlry));
        this.output.writeInDB(this.emp);
    }
}
