package be.heh.std.epm.application.port.out;

import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.Receipt;
import be.heh.std.epm.domain.TimeCard;

public interface OutPersistence {
    void saveEmployee(Employee emp) throws Exception;
    void saveReceipt(int id, Receipt receipt) throws Exception;
    void saveTimeCard(int id, TimeCard timeCard) throws Exception;
    void deleteEmployee(int id) throws Exception;
    void updateAddress(int id, String newAddress) throws Exception;
    void updateName(int id, String newName) throws Exception;
    void updateToCommissioned(int id, double salary, double commissionRate) throws Exception;
    void updateToHourly(int id, double rate) throws Exception;
    void updateToSalaried(int id, double salary) throws Exception;
    void updateToDirectDepositMethod(int id, String bank, String iban) throws Exception;
    void updateToMailMethod(int id, String email) throws Exception;
    Employee getData(int id) throws Exception;
    boolean dataExists(int id) throws Exception;
}
