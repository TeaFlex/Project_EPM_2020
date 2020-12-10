package be.heh.std.epm.application.service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, AddEmployee> {
    @Override
    public boolean isValid(AddEmployee value, ConstraintValidatorContext context) {
        return Objects.isNull(value.getEmail()) && (Objects.isNull(value.getBank()) || Objects.isNull(value.getIban()));
    }
}
