package be.heh.std.epm.application.service;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PaymentMethodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPaymentMethod {
    String message() default "{error.address}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
