package be.heh.std.epm.domain;

public class Employee {

    private int empID;
    private String name;
    private String address;
    private PaymentClassification paymentClassification;
    private PaymentMethod paymentMethod;
    private PaymentSchedule paymentSchedule;

    public Employee(int empID, String name, String address) {
        this.empID = empID;
        this.name = name;
        this.address = address;
    }

    public double calculatePay() {

        return this.paymentClassification.getSalary(); // Attention ! C'est presque la bonne réponse.
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

    public void setPaymentClassification(PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }
}
