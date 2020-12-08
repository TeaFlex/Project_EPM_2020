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
    public void postTimeCard(int id, DataTimeCard dataTimeCard) throws Exception {
        out.save(id, dataTimeCard.toTimeCard());
    }

    @Override
    public void postSaleReceipt(int id, DataReceipt dataReceipt) throws Exception {
        out.save(id, dataReceipt.toReceipt());
    }

    @Override
    public void updateName(int id, String newName) throws Exception {

    }

    @Override
    public void updateAddress(int id, String newAddress) throws Exception {

    }

    @Override
    public void updateToHourly(int id, double rate) throws Exception {

    }

    @Override
    public void updateToSalaried(int id, double salary) throws Exception {

    }

    @Override
    public void updateToCommissioned(int id, double salary, double commissionRate) throws Exception {

    }

    @Override
    public void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception {

    }

    @Override
    public void updateToMailMethod(int id, String email) throws Exception {

    }

}
