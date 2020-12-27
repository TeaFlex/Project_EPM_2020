package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PayDayTest {

    private OutPersistence db;
    private AddEmployee add;
    private PayDay payDay;
    private int id;

    @Before
    public void setUp(){
        db = new ListPersistence();
        id = 11;
    }

    public void setBasicInfos() {
        add.setId(id);
        add.setName("Luigi");
        add.setAddress("Maison de Luigi");
        add.setEmail("Luigi@MushroomKingdom.com");
    }

    @Test
    public void payDayForSalaried() throws Exception {
        add = new AddSalariedEmployee();
        ((AddSalariedEmployee) add).setSalary(1000);
        setBasicInfos();
        add.execute(db);

        payDay = new PayDay();

        payDay.setId(id);
        PayCheck payCheck = payDay.payday(db);
        assertEquals(LocalDate.now(), payCheck.getDate());
        assertEquals(1000, payCheck.getPay(), 0);

    }

    @Test
    public void payDayForCommissioned() throws Exception {
        add = new AddCommissionEmployee();
        ((AddCommissionEmployee) add).setSalary(1000);
        ((AddCommissionEmployee) add).setCommissionRate(0.5);
        setBasicInfos();
        add.execute(db);

        payDay = new PayDay();

        payDay.setId(id);
        PayCheck payCheck = payDay.payday(db);
        assertEquals(LocalDate.now(), payCheck.getDate());
        assertEquals(1000, payCheck.getPay(), 0);


        PostReceipt post = new PostReceipt();
        post.setPrice(100);
        post.setDate(LocalDate.now());
        post.setId(id);
        post.execute(db);

        payDay = new PayDay();

        payDay.setId(id);
        payCheck = payDay.payday(db);
        assertEquals(LocalDate.now(), payCheck.getDate());
        assertEquals(1050, payCheck.getPay(), 0);
    }

    @Test
    public void payDayForHourly() throws Exception {
        add = new AddHourlyEmployee();
        ((AddHourlyEmployee) add).setRate(100);
        setBasicInfos();
        add.execute(db);

        payDay = new PayDay();

        payDay.setId(id);
        PayCheck payCheck = payDay.payday(db);
        assertEquals(LocalDate.now(), payCheck.getDate());
        assertEquals(0, payCheck.getPay(), 0);


        PostTimeCard post = new PostTimeCard();
        post.setHours(10);
        post.setDate(LocalDate.now());
        post.setId(id);
        post.execute(db);

        payDay = new PayDay();

        payDay.setId(id);
        payCheck = payDay.payday(db);
        assertEquals(LocalDate.now(), payCheck.getDate());
        assertEquals(1100, payCheck.getPay(), 0);
    }
}
