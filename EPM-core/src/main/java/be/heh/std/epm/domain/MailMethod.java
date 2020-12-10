package be.heh.std.epm.domain;

import lombok.Value;

@Value
public class MailMethod implements PaymentMethod {

    private String email;

    public MailMethod(String email) {
        this.email = email;
    }

    @Override
    public String getMethodInfos() {
        return String.format("mail : %s", email);
    }
}
