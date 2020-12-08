package be.heh.std.epm.application.port.in;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataReceipt;
import be.heh.std.epm.application.data.DataTimeCard;

public interface InUseCase {
    void addEmployee(DataEmployee dataEmployee) throws Exception;
    void deleteEmployee(int id) throws Exception;
    void postTimeCard(int id, DataTimeCard dataTimeCard) throws Exception;
    void postSaleReceipt(int id, DataReceipt dataReceipt) throws Exception;
    void updateName(int id, String newName) throws Exception;
    void updateAddress(int id, String newAddress) throws Exception;
    void updateToHourly(int id, double rate) throws Exception;
    void updateToSalaried(int id, double salary) throws Exception;
    void updateToCommissioned(int id, double salary, double commissionRate) throws Exception;
    void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception;
    void updateToMailMethod(int id, String email) throws Exception;
}
