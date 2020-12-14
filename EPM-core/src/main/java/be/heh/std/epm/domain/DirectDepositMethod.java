package be.heh.std.epm.domain;

import lombok.Value;

@Value
public class DirectDepositMethod implements PaymentMethod {

    private String bank;
    private String iban;

    public DirectDepositMethod(String bank, String iban) {
        this.bank = bank;
        this.iban = iban;
    }

    @Override
    public String getMethodInfos() {
        return String.format("direct deposit into %s : %s", bank, iban);
    }
}
