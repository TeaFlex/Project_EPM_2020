package be.heh.std.epm.application.service;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.application.test_adapter.ListPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateEmployeeTest {

    private OutPersistence db;
    private UpdateEmployee up;
    private AddEmployee add;
    private int id = 29;

    @Before
    public void setUp() {
        db = new ListPersistence();
        add = new AddSalariedEmployee();
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
    public void updatingEmpName() throws Exception {
        setBasicInfosMail();

        up = new UpdateName();
        up.setId(id);
        ((UpdateName)up).setNewName("King Koopa");
        up.execute(db);

        Employee dbEmployee = db.getData(id);

        assertNotSame("Bowser", dbEmployee.getName());
        assertSame("King Koopa", dbEmployee.getName());
    }

    @Test
    public void updatingEmpAddress() throws Exception {
        setBasicInfosMail();

        up = new UpdateAddress();
        up.setId(id);
        ((UpdateAddress)up).setNewAddress("Royaume Champignon");
        up.execute(db);

        Employee dbEmployee = db.getData(id);

        assertNotSame("Royaume Koopa", dbEmployee.getAddress());
        assertSame("Royaume Champignon", dbEmployee.getAddress());
    }

    @Test
    public void updatingEmpToSalaried() throws Exception{
        add = new AddHourlyEmployee();
        setBasicInfosMail();

        up = new UpdateToSalaried();
        up.setId(id);
        ((UpdateToSalaried)up).setSalary(1234);
        up.execute(db);

        Employee dbEmployee = db.getData(id);
        double dbInfos = ((SalariedClassification) dbEmployee.getPaymentClassification()).getSalary();

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentClassification() instanceof SalariedClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof MonthlyPaymentSchedule);
        assertEquals(1234, dbInfos, 0.0);
    }

    @Test
    public void updatingEmpToHourly() throws Exception{
        setBasicInfosMail();

        up = new UpdateToHourly();
        up.setId(id);
        ((UpdateToHourly)up).setRate(123);
        up.execute(db);

        Employee dbEmployee = db.getData(id);
        double dbInfos = ((HourlyClassification) dbEmployee.getPaymentClassification()).getSalary();

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentClassification() instanceof HourlyClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
        assertEquals(123, dbInfos, 0.0);
    }

    @Test
    public void updatingEmpToCommissioned() throws Exception {
        setBasicInfosMail();

        up = new UpdateToCommissioned();
        up.setId(id);
        ((UpdateToCommissioned)up).setSalary(1234);
        ((UpdateToCommissioned)up).setCommissionRate(0.5);
        up.execute(db);

        Employee dbEmployee = db.getData(id);
        double[] dbInfos = {((CommissionClassification) dbEmployee.getPaymentClassification()).getSalary(),
                ((CommissionClassification) dbEmployee.getPaymentClassification()).getCommissionRate()};

        assertTrue(dbEmployee.getPaymentClassification() instanceof CommissionClassification);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof BiweeklyPaymentSchedule);
        assertEquals(1234, dbInfos[0], 0.0);
        assertEquals(0.5, dbInfos[1], 0.0);
    }

    @Test
    public void updatingMethodToDirectDeposit() throws Exception{
        setBasicInfosMail();

        up = new UpdateToDirectDepositMethod();
        up.setId(id);
        ((UpdateToDirectDepositMethod)up).setBank("KoopaBank");
        ((UpdateToDirectDepositMethod)up).setIban("KO678997654357");
        up.execute(db);

        Employee dbEmployee = db.getData(id);
        String[] dbInfos = {((DirectDepositMethod) dbEmployee.getPaymentMethod()).getBank(),
                ((DirectDepositMethod) dbEmployee.getPaymentMethod()).getIban()};

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentMethod() instanceof DirectDepositMethod);
        assertSame("KoopaBank", dbInfos[0]);
        assertSame("KO678997654357", dbInfos[1]);
    }

    @Test
    public void updatingMethodToMail() throws Exception{
        setBasicInfosBank();

        up = new UpdateToMailMethod();
        up.setId(id);
        ((UpdateToMailMethod)up).setEmail("BowerKing@koopapolis.com");
        up.execute(db);

        Employee dbEmployee = db.getData(id);
        String dbInfos = ((MailMethod) dbEmployee.getPaymentMethod()).getEmail();

        assertTrue(db.dataExists(id));
        assertTrue(dbEmployee.getPaymentMethod() instanceof MailMethod);
        assertSame("BowerKing@koopapolis.com", dbInfos);
    }
}
