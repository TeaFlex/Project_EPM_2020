package be.heh.std.epm.application.port.in;

import be.heh.std.epm.application.data.DataEmployee;

import java.time.LocalDate;

public interface InUseCase {
    void addEmployee(DataEmployee e) throws Exception;
    void deleteEmployee(int id) throws Exception;
    void postTimeCard(int id, LocalDate date, double hours) throws Exception;
    void postSaleReceipt(int id, LocalDate date, double price) throws Exception;
}
