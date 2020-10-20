package be.heh.std.epm.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MonthlyPaymentySchedule implements PaymentSchedule {
    @Override
    public GregorianCalendar getPayementDate() {
        Calendar payday = new GregorianCalendar();
        return null;//payday.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
