package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PostReceiptTest {

    private OutPersistence db;
    private PostReceipt post;
    private AddEmployee add;
    private int id = 29;

    @Before
    public void setUp() {
        db = new ListPersistence();
        add = new AddCommissionEmployee();
        post = new PostReceipt();
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
    public void postReceipt() throws Exception {
        setBasicInfosMail();

        LocalDate date = LocalDate.of(2020,12,12);
        Receipt receipt = new Receipt(date, 200);

        post.setId(id);
        post.setDate(date);
        post.setPrice(200);
        post.execute(db);

        Employee dbEmployee = db.getData(id);
        Receipt dbReceipt = ((CommissionClassification)dbEmployee.getPaymentClassification()).getReceipts().get(0);

        assertEquals(receipt, dbReceipt);
        assertEquals(LocalDate.of(2020,12,12), dbReceipt.getDate());
        assertEquals(200, dbReceipt.getPrice(), 0.0);
    }

    @Test(expected = Exception.class)
    public void postReceiptToWrongClassificationEmployee() throws Exception{
        add = new AddSalariedEmployee();
        setBasicInfosMail();

        post.setId(id);
        post.setDate(LocalDate.now());
        post.setPrice(666);
        post.execute(db);
    }
}
