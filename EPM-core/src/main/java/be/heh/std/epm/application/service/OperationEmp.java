package be.heh.std.epm.application.service;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.in.InUseCase;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;

import java.time.LocalDate;

public class OperationEmp implements InUseCase {

    private OutPersistence out;

    public OperationEmp (OutPersistence o) {
        this.out = o;
    }

    @Override
    public void addEmployee(DataEmployee e) throws Exception {
        try {
            Employee finalEmployee = e.toEmployee();
            this.out.save(finalEmployee);
        }
        catch (Exception err){
            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void deleteEmployee(int id) throws Exception {
        try{
            this.out.delete(id);
        }
        catch(Exception err){
            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void postTimeCard(int id, LocalDate date, double hours) throws Exception {
        Employee e = this.out.getData(id);
        if(e.getPaymentClassification() instanceof HourlyClassification){

            ((HourlyClassification) e.getPaymentClassification()).addTimeCard(new TimeCard(date, hours));
            this.out.replace(e);
        }
        else {
            throw new Exception("The chosen employee is not from the classification HOURLY.");
        }
    }

    @Override
    public void postSaleReceipt(int id, LocalDate date, double price) throws Exception {
        Employee e = this.out.getData(id);
        if(e.getPaymentClassification() instanceof CommissionClassification) {
            ((CommissionClassification) e.getPaymentClassification()).addReceipt(new Receipt(date, price));
            this.out.replace(e);
        }
        else{
            throw new Exception("The chosen employee is not from the classification COMMISSION.");
        }
    }

}
