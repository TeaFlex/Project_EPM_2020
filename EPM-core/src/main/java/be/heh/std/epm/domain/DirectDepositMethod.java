package be.heh.std.epm.domain;

public class DirectDepositMethod implements PaymentMethod {

    private String bank;
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
