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
    public double getSalary() {
        double temp = 0;
        for(TimeCard timeCard : timeCards){
            temp += (timeCard.getHours() > 8) ?
                    (8*salary)+((timeCard.getHours()-8)*1.5*salary) : timeCard.getHours()*salary;
        }
        return temp;
    }
}
