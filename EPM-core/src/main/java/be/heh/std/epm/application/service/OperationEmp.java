package be.heh.std.epm.application.service;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataReceipt;
import be.heh.std.epm.application.data.DataTimeCard;
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
    public void addEmployee(DataEmployee dataEmployee) throws Exception {
        this.out.save(dataEmployee.toEmployee());
    }

    @Override
    public void deleteEmployee(int id) throws Exception {
        this.out.delete(id);
    }

    @Override
    public <T> void updateEmployee(int id, String field, T value) throws Exception {
        Employee e = out.getData(id);
    }

    @Override
    public void postTimeCard(int id, DataTimeCard dataTimeCard) throws Exception {
        Employee e = out.getData(id);
        if (e.getPaymentClassification() instanceof HourlyClassification) {
            ((HourlyClassification) e.getPaymentClassification()).addTimeCard(dataTimeCard.toTimeCard());
            out.replace(e);
        } else {
            throw new Exception(String.format("The chosen employee is from %s.",
                    e.getPaymentClassification().getClass()));
        }
    }

    @Override
    public void postSaleReceipt(int id, DataReceipt dataReceipt) throws Exception {
        Employee e = out.getData(id);
        if (e.getPaymentClassification() instanceof CommissionClassification) {
            ((CommissionClassification) e.getPaymentClassification()).addReceipt(dataReceipt.toReceipt());
            out.replace(e);
        } else {
            throw new Exception(String.format("The chosen employee is from %s.",
                    e.getPaymentClassification().getClass()));
        }
    }

}
