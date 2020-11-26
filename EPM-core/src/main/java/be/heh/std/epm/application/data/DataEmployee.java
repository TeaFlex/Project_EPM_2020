package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.DirectDepositMethod;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.MailMethod;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public abstract class DataEmployee {
    @NotNull
    private int id;
    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String address;
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String bank;
    @NotEmpty
    private String iban;

    public Employee toEmployee() throws Exception {
        Employee employee = new Employee(id, name, address);
        if (!Objects.isNull(email)) {
            employee.setPaymentMethod(new MailMethod(email));
        } else if (!Objects.isNull(bank) && !Objects.isNull(iban)) {
            employee.setPaymentMethod(new DirectDepositMethod(bank, iban));
        } else {
            throw new Exception("Payment method information is either missing or incomplete.");
        }
        return employee;
    }

}
