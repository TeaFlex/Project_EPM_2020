package be.heh.std.epm.domain;

public class MailMethod implements PaymentMethod {

    private String email;

    public MailMethod(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("mail : %s", email);
    }
}
