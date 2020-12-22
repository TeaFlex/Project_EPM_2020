package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PostTimeCardTest {

    private OutPersistence db;
    private PostTimeCard post;
    private AddEmployee add;
    private int id = 29;

    @Before
    public void setUp() {
        db = new ListPersistence();
        add = new AddHourlyEmployee();
        post = new PostTimeCard();
    }

    public void setBasicInfosMail() throws Exception {
        add.setId(id);
        add.setName("Bowser");
        add.setAddress("Royaume Koopa");
        add.setEmail("agrougrou@koopapolis.com");
        add.execute(db);
    }

    public void setBasicInfosBank() throws Exception {
        add.setId(id);
        add.setName("Bowser");
        add.setAddress("Royaume Koopa");
        add.setBank("KoopaBank");
        add.setIban("KO123445677886");
        add.execute(db);
    }

    @Test
    public void postTimeCard() throws Exception {
        setBasicInfosMail();

        LocalDate date = LocalDate.of(2020,12,12);
        TimeCard timeCard = new TimeCard(date, 12);

        post.setId(id);
        post.setDate(date);
        post.setHours(12);
        post.execute(db);

        Employee dbEmployee = db.getData(id);
        TimeCard dbTimeCard = ((HourlyClassification)dbEmployee.getPaymentClassification()).getTimeCards().get(0);

        assertEquals(timeCard, dbTimeCard);
        assertEquals(LocalDate.of(2020,12,12), dbTimeCard.getDate());
        assertEquals(12, dbTimeCard.getHours(), 0.0);
    }

    @Test(expected = Exception.class)
    public void postTimeCardToWrongClassificationEmployee() throws Exception{
        add = new AddSalariedEmployee();
        setBasicInfosMail();

        post.setId(id);
        post.setDate(LocalDate.now());
        post.setHours(12);
        post.execute(db);
    }
}
