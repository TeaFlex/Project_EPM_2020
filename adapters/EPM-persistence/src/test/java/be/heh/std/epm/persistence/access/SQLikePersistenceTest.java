package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SQLikePersistenceTest {

    private OutPersistence db;
    private Employee employee;

    @Before
    public void setup() {
        try{
            db = new H2Persistence();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        employee = new Employee(1, "Link", "Hyrule");
        setBasicInfos();
        setBankMethod();
        //Note: si l'on ne modifie pas l'employé test, celui-ci sera automatiquement Salaried avec un DirectDepositMethod.
    }

    public void setBankMethod() {
        employee.setPaymentMethod(new DirectDepositMethod("Belfius", "BE23455677788"));
    }

    public void setMailMethod() {
        employee.setPaymentMethod(new MailMethod("test@test.test"));
    }

    public void setBasicInfos() {
        employee.setPaymentClassification(new SalariedClassification(1234));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
    }

    @Test
    public void saveEmployee() throws Exception{
        db.saveEmployee(employee);
        //Vérification de l'existence de l'employé dans la db et de sa similarité avec l'objet initial.
        assertTrue(db.dataExists(employee.getEmpID()));
        assertEquals(employee, db.getData(employee.getEmpID()));
    }

    @Test(expected = Exception.class)
    public void badSaveEmployee() throws Exception {
        //Double ajout d'un même employé.
        db.saveEmployee(employee);
        db.saveEmployee(employee);
    }

    @Test
    public void deleteEmployee() throws Exception{
        db.saveEmployee(employee);
        //Vérification de l'existence de l'employé dans la db.
        assertTrue(db.dataExists(employee.getEmpID()));

        db.deleteEmployee(employee.getEmpID());
        //Vérification de l'absence de l'employé dans la db.
        assertFalse(db.dataExists(employee.getEmpID()));
    }

    @Test(expected = Exception.class)
    public void badDeleteEmployee() throws Exception {
        //Suppression d'un employé inexistant dans la db.
        db.deleteEmployee(employee.getEmpID());
    }

    @Test
    public void updateAdressEmployee() throws Exception {
        db.saveEmployee(employee);
        db.updateAddress(employee.getEmpID(), "Ile de Koridaï");

        //Vérification de la différence des deux adresses et de l'entièreté de la nouvelle.
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertNotSame(employee.getAddress(), dbEmployee.getAddress());
        assertEquals(dbEmployee.getAddress(), "Ile de Koridaï");
    }

    @Test
    public void updateNameEmployee() throws Exception {
        db.saveEmployee(employee);
        db.updateName(employee.getEmpID(), "Squalala");

        //Vérification de la différence des deux noms et de l'entièreté du nouveau.
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertNotSame(employee.getName(), dbEmployee.getName());
        assertEquals(dbEmployee.getName(), "Squalala");
    }

    @Test
    public void updateToSalariedEmployee() throws Exception {
        //Ajout d'un employé non-Salaried
        employee.setPaymentClassification(new HourlyClassification(123));
        employee.setPaymentSchedule(new WeeklyPaymentSchedule());
        db.saveEmployee(employee);

        //Passage vers la classification Salaried
        db.updateToSalaried(employee.getEmpID(), 3000);

        //Vérification du contenu de la DB
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertTrue(dbEmployee.getPaymentClassification() instanceof SalariedClassification);
        assertEquals(3000, dbEmployee.getPaymentClassification().getSalary(), 0);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof MonthlyPaymentSchedule);
    }

    @Test
    public void updateToHourlyEmployee() throws Exception {
        //Ajout d'un employé non-Hourly
        employee.setPaymentClassification(new CommissionClassification(1000, 0.2));
        employee.setPaymentSchedule(new BiweeklyPaymentSchedule());
        db.saveEmployee(employee);

        //Passage vers la classification Hourly
        db.updateToHourly(employee.getEmpID(), 250);

        //Vérification du contenu de la DB
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertTrue(dbEmployee.getPaymentClassification() instanceof HourlyClassification);
        assertEquals(250, dbEmployee.getPaymentClassification().getSalary(), 0);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof WeeklyPaymentSchedule);
    }

    @Test
    public void updateToCommissionnedEmployee() throws Exception {
        //Ajout d'un employé non-Salaried
        employee.setPaymentClassification(new SalariedClassification(1200));
        employee.setPaymentSchedule(new MonthlyPaymentSchedule());
        db.saveEmployee(employee);

        //Passage vers le type Salaried
        db.updateToCommissioned(employee.getEmpID(), 2000, 0.6);

        //Vérification du contenu de la DB
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertTrue(dbEmployee.getPaymentClassification() instanceof CommissionClassification);
        assertEquals(2000, dbEmployee.getPaymentClassification().getSalary(), 0);
        assertEquals(0.6, ((CommissionClassification)dbEmployee.getPaymentClassification()).getCommissionRate(), 0);
        assertTrue(dbEmployee.getPaymentSchedule() instanceof BiweeklyPaymentSchedule);
    }

    @Test
    public void updateToDirectDeposit() throws Exception {
        //Ajout d'un employé avec une méthode de payement par mail.
        setMailMethod();
        db.saveEmployee(employee);

        //Passage vers le dépôt direct
        db.updateToDirectDepositMethod(employee.getEmpID(), "RupeeBank", "HY123456789");

        //Vérification du contenu de la DB
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertTrue(dbEmployee.getPaymentMethod() instanceof DirectDepositMethod);
        assertSame("RupeeBank", ((DirectDepositMethod)dbEmployee.getPaymentMethod()).getBank());
        assertSame("HY123456789", ((DirectDepositMethod)dbEmployee.getPaymentMethod()).getIban());
    }

    @Test
    public void updateToMail() throws Exception {
        //Ajout d'un employé avec une méthode de payement directe.
        setBankMethod();
        db.saveEmployee(employee);

        //Passage vers le mail
        db.updateToMailMethod(employee.getEmpID(), "Link@hyrule.com");

        //Vérification du contenu de la DB
        Employee dbEmployee = db.getData(employee.getEmpID());
        assertTrue(dbEmployee.getPaymentMethod() instanceof MailMethod);
        assertSame("Link@hyrule.com", ((MailMethod)dbEmployee.getPaymentMethod()).getEmail());
    }

    @Test
    public void saveTimeCard() throws Exception {
        //Sauvegarde d'un employé et de sa TimeCard.
        employee.setPaymentClassification(new HourlyClassification(1234));
        db.saveEmployee(employee);
        TimeCard tc  = new TimeCard(LocalDate.now(), 11);
        db.saveTimeCard(employee.getEmpID(), tc);

        //Vérification que l'objet initial et l'objet de la db sont différents.
        assertNotEquals(employee, db.getData(employee.getEmpID()));

        //Après ajout de la TimeCard à l'objet initial, on vérifie que les deux employés soient bien similaires.
        ((HourlyClassification)employee.getPaymentClassification()).addTimeCard(tc);
        assertEquals(employee, db.getData(employee.getEmpID()));
    }

    @Test(expected = Exception.class)
    public void saveSameTimecard() throws Exception {
        //Sauvegarde double d'une même Timecard
        employee.setPaymentClassification(new HourlyClassification(1234));
        db.saveEmployee(employee);
        TimeCard tc  = new TimeCard(LocalDate.now(), 11);
        db.saveTimeCard(employee.getEmpID(), tc);
        db.saveTimeCard(employee.getEmpID(), tc);
    }

    @Test(expected = Exception.class)
    public void badTypeTimecard() throws Exception {
        //Tentative de sauvegarde d'une TimeCard pour un mauvais type d'employé.
        db.saveEmployee(employee);
        TimeCard tc  = new TimeCard(LocalDate.now(), 11);
        db.saveTimeCard(employee.getEmpID(), tc);
    }

    @Test(expected = Exception.class)
    public void UnexistentEmpTimecard() throws Exception {
        //Tentative de sauvegarde d'une TimeCard pour un employé inexistant.
        TimeCard tc  = new TimeCard(LocalDate.now(), 11);
        db.saveTimeCard(employee.getEmpID(), tc);
    }

    @Test
    public void saveReceipt() throws Exception {
        //Sauvegarde d'un employé et de sa Receipt.
        employee.setPaymentClassification(new CommissionClassification(1234, 0.5));
        db.saveEmployee(employee);
        Receipt rc  = new Receipt(LocalDate.now(), 111);
        db.saveReceipt(employee.getEmpID(), rc);

        //Vérification que l'objet initial et l'objet de la db sont différents.
        assertNotEquals(employee, db.getData(employee.getEmpID()));

        //Après ajout de la Receipt à l'objet initial, on vérifie que les deux employés soient bien similaires.
        ((CommissionClassification)employee.getPaymentClassification()).addReceipt(rc);
        assertEquals(employee, db.getData(employee.getEmpID()));
    }

    @Test(expected = Exception.class)
    public void saveSameReceipt() throws Exception {
        //Sauvegarde double d'une même Receipt
        employee.setPaymentClassification(new CommissionClassification(1234, 0.5));
        db.saveEmployee(employee);
        Receipt rc  = new Receipt(LocalDate.now(), 111);
        db.saveReceipt(employee.getEmpID(), rc);
        db.saveReceipt(employee.getEmpID(), rc);
    }

    @Test(expected = Exception.class)
    public void badTypeReceipt() throws Exception {
        //Tentative de sauvegarde d'une Receipt pour un mauvais type d'employé.
        db.saveEmployee(employee);
        Receipt rc  = new Receipt(LocalDate.now(), 111);
        db.saveReceipt(employee.getEmpID(), rc);
    }

    @Test(expected = Exception.class)
    public void UnexistentEmpReceipt() throws Exception {
        //Tentative de sauvegarde d'une Receipt pour un employé inexistant.
        Receipt rc  = new Receipt(LocalDate.now(), 111);
        db.saveReceipt(employee.getEmpID(), rc);
    }

    //TODO: à compléter avec le reste des tests.
}