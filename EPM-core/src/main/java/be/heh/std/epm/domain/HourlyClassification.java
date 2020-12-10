package be.heh.std.epm.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class HourlyClassification implements PaymentClassification {

    @Getter
    private double rate;
    @Getter
    private List<TimeCard> timeCards;

    public HourlyClassification(double rate) {
        this.rate = rate;
        timeCards = new ArrayList<>();
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCards.add(timeCard);
    }

    @Override
    public double getPay(DateRange dateRange) {
        double pay = 0;
        for (TimeCard timeCard : timeCards) {
            if (dateRange.isWithinRange(timeCard.getDate())) {
                if (timeCard.getHours() > 8) pay += (8 * rate) + ((timeCard.getHours() - 8) * 1.5 * rate);
                else pay += timeCard.getHours() * rate;
            }
        }
        return pay;
    }
}
