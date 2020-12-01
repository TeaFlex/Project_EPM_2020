package be.heh.std.epm.domain;

import lombok.Getter;

import java.util.*;

public class CommissionClassification extends SalariedClassification {

    @Getter
    private List<Receipt>receipts;
    @Getter
    private double commissionRate;

    public CommissionClassification(double salary, double commissionRate) {
        super(salary);
        this.receipts = new ArrayList<>();
        this.commissionRate = commissionRate;
    }

    public void addReceipt(Receipt receipt) {
        this.receipts.add(receipt);
    }

    @Override
    public double getPay(DateRange dateRange) {
        double pay = 0;
        for (Receipt receipt : receipts) {
            pay += (dateRange.isWithinRange(receipt.getDate())) ? commissionRate * receipt.getPrice() : 0;
        }
        return pay + super.getPay(dateRange);
    }
}
