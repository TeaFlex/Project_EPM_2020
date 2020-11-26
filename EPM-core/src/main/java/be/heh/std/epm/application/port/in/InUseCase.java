package be.heh.std.epm.application.port.in;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.data.DataReceipt;
import be.heh.std.epm.application.data.DataTimeCard;

import java.time.LocalDate;

public interface InUseCase {
    void addEmployee(DataEmployee dataEmployee) throws Exception;
    void deleteEmployee(int id) throws Exception;
    void postTimeCard(int id, DataTimeCard dataTimeCard) throws Exception;
    void postSaleReceipt(int id, DataReceipt dataReceipt) throws Exception;
    <T> void updateEmployee(int id, String field, T value) throws Exception;
}
