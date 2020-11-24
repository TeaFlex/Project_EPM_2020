package be.heh.std.epm.application.service;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.HourlyClassification;
import be.heh.std.epm.domain.TimeCard;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

public class OperationEmp {

    private  OutPersistence out;

    public OperationEmp (OutPersistence o) {
        this.out = o;
    }

    public void addEmployee(DataEmployee e) {
        Employee finalemp = new Employee(e.getId(), e.getName(), e.getAddress());
        finalemp.setPaymentSchedule(e.getPaymentSchedule());
        finalemp.setPaymentClassification(e.getPaymentClassification());
        finalemp.setPaymentMethod(e.getPaymentMethod());
        this.out.save(finalemp);
    }

    public void deleteEmployee(int id) {
        this.out.delete(id);
    }

    public void postTimeCard(int id, LocalDate date, double hours) {
        Employee e = this.out.getData(id);
        if(e.getPaymentClassification() instanceof HourlyClassification){

            ((HourlyClassification) e.getPaymentClassification()).addTimeCard(new TimeCard(date, hours));
            this.out.replace(e);
        }
        else {
            //throw error
        }
    }
    
}
