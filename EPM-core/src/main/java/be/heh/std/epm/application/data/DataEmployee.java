package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.PaymentClassification;
import be.heh.std.epm.domain.PaymentMethod;
import be.heh.std.epm.domain.PaymentSchedule;
import lombok.Value;

@Value
public class DataEmployee {
    int id;
    String name;
    String address;
    PaymentClassification paymentClassification;
    PaymentMethod paymentMethod;
    PaymentSchedule paymentSchedule;
}
