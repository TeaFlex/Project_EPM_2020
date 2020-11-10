package be.heh.std.epm.application.service;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

public class OperationEmp {

    public static void addEmp(DataEmployee e, OutPersistence out){

        Employee finalEmp = new Employee(e.getId(), e.getName(), e.getAddress());
        finalEmp.setPaymentSchedule(e.getPaymentSchedule());
        finalEmp.setPaymentClassification(e.getPaymentClassification());
        finalEmp.setPaymentMethod(e.getPaymentMethod());
        out.save(finalEmp);
    }

    public static void delEmp(int id, OutPersistence out) {
        out.delete(id);
    }
}
