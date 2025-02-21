package be.heh.std.epm.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
public class CommissionClassification extends SalariedClassification {


    private List<Receipt>receipts;
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
