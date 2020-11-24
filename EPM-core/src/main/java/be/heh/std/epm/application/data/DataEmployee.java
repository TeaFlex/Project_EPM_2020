package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.DirectDepositMethod;
import be.heh.std.epm.domain.Employee;
import be.heh.std.epm.domain.MailMethod;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public abstract class DataEmployee {
    @Getter @Setter @NotNull
    private int id;
    @Getter @Setter @NotNull @NotEmpty
    private String name;
    @Getter @Setter @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String bank;
    @NotEmpty
    private String iban;

    public Employee toEmployee() {
        Employee employee = new Employee(id, name, address);
        if (!Objects.isNull(email)) {
            employee.setPaymentMethod(new MailMethod(email));
        } else if (!Objects.isNull(bank) && !Objects.isNull(iban)) {
            employee.setPaymentMethod(new DirectDepositMethod(bank, iban));
        } else {
            // TODO: Exception ?!!
        }
        return employee;
    }
}
