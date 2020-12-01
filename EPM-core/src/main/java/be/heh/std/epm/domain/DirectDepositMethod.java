package be.heh.std.epm.domain;

import lombok.Getter;

public class DirectDepositMethod implements PaymentMethod {

    @Getter
    private String bank;
    @Getter
    private String iban;

    public DirectDepositMethod(String bank, String iban) {
        this.bank = bank;
        this.iban = iban;
    }

    @Override
    public String toString() {
        return String.format("direct deposit into %s : %s", bank, iban);
    }
}
