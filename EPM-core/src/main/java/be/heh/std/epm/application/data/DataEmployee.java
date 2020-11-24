package be.heh.std.epm.application.data;

import be.heh.std.epm.domain.PaymentClassification;
import be.heh.std.epm.domain.PaymentMethod;
import be.heh.std.epm.domain.PaymentSchedule;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


public abstract class DataEmployee {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private PaymentMethod paymentMethod;
    @Getter
    protected PaymentClassification paymentClassification;
    @Getter
    protected PaymentSchedule paymentSchedule;

    public DataEmployee(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
