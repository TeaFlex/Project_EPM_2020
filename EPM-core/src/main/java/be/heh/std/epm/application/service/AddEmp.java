package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

public abstract class AddEmp {

    protected OutPersistence output;
    protected Employee emp;

    protected AddEmp(int id, String name, String address, OutPersistence output){
        this.emp = new Employee(id, name, address);
        this.output = output;
    }
}
