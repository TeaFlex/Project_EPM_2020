package be.heh.std.epm.domain;

import lombok.Getter;

public class MailMethod implements PaymentMethod {

    @Getter
    private String email;

    public MailMethod(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("mail : %s", email);
    }
}
