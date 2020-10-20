package be.heh.std.epm.domain;

import java.util.ArrayList;
import java.util.List;

public class HourlyClassification implements PaymentClassification {

    private double salary;
    private List<TimeCard> timeCards;

    public HourlyClassification(double salary) {
        this.salary = salary;
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
                if (timeCard.getHours() > 8) pay += (8 * salary) + ((timeCard.getHours() - 8) * 1.5 * salary);
                else pay += timeCard.getHours() * salary;
            }
        }
        return pay;
    }
}
