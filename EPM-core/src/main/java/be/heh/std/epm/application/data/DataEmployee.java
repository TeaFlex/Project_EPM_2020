package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotEmpty @Pattern(regexp = "^[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?$")
    private String iban;

    public DataEmployee(Employee employee) {
        id = employee.getEmpID();
        name = employee.getName();
        address = employee.getAddress();
        PaymentMethod paymentMethod = employee.getPaymentMethod();
        if (paymentMethod instanceof MailMethod) {
            email = ((MailMethod) paymentMethod).getEmail();
        } else if (paymentMethod instanceof DirectDepositMethod) {
            bank = ((DirectDepositMethod) paymentMethod).getBank();
            iban = ((DirectDepositMethod) paymentMethod).getIban();
        }
    }

    public DataEmployee() {

    }

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
