package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import model.Contact;
import model.User;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.io.IOException;

public class PhoneBookTest extends BaseTest {
    @Test(groups = "group1",description = "The test checks the empty field warning declaration.")
    public void registrationWithoutPassword() throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");

        MainPage mainPage = new MainPage(getDriver());

        Allure.step("Click by Login button");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());

        Allure.step("Click by Reg button");
        String expectedString = "Wrong";
        Alert alert = loginPage.fillEmailField(EmailGenerator.generateEmail(10, 5, 3))
                .clickByRegistartionButton();
        boolean isAlertHandledt = AlertHandler.handAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandledt);
        Thread.sleep(3000);
    }

    @Test
    public void loginOfAnExistingUserAddContact() throws Exception {
        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        Allure.step("Step 3");
        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact contact = new Contact(NameAndLastNameGenerator.generateName()
                , NameAndLastNameGenerator.generateLastName()
                , PhoneNumberGenerator.generatePhoneNumber()
                , EmailGenerator.generateEmail(10, 5, 3)
                , AddressGenerator.generateAddress()
                , "New contact");
        //System.out.println(contact.toString());

        addPage.fillFormAndSave(contact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(contact));
        TakeScreen.takeScreenshot(getDriver(),"screen");
        Thread.sleep(3000);


    }

    @Test
    public void testSuccessfullyRegistration() {

        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(EmailGenerator.generateEmail(10, 5, 3))
                .fillPasswordField(PasswordStringGenerator.generateString())
                .clickByRegistartionButton();
        Allure.step("Step 3");
        Assert.assertTrue(new ContactsPage(getDriver()).isElementPresent());
        TakeScreen.takeScreenshot(getDriver(),"screenreg");
    }

    @Test
    public void testRemoveContact() throws Exception {
        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        Allure.step("Step 3");
        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact contact = new Contact(NameAndLastNameGenerator.generateName()
                , NameAndLastNameGenerator.generateLastName()
                , PhoneNumberGenerator.generatePhoneNumber()
                , EmailGenerator.generateEmail(10, 5, 3)
                , AddressGenerator.generateAddress()
                , "New contact");
        addPage.fillFormAndSave(contact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        int count = contactsPage.removeContact();
        Assert.assertEquals(count, 1);

    }

    @Test
    public void testRemoveContactAll() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        contactsPage.removeContactAll();
        Assert.assertTrue((contactsPage.isNoContact()));
    }

    @Test
    public void testRemoveContactTwo() throws IOException, InterruptedException {
        String filename = "contactDataFile.ser";
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());

        mainPage.openTopMenu(TopMenuItem.ADD.toString());
        Contact contact = new Contact(NameAndLastNameGenerator.generateName()
                , NameAndLastNameGenerator.generateLastName()
                , PhoneNumberGenerator.generatePhoneNumber()
                , EmailGenerator.generateEmail(10, 5, 3)
                , AddressGenerator.generateAddress()
                , "New contact");

        addPage.fillFormAndSave(contact);
        Contact.serializationContact(contact, filename);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Contact deserContact = Contact.deserializationContact(filename);
        System.out.println(Contact.deserializationContact(filename).toString());
        int count = contactsPage.deleteContactByPhoneNumberOrName(deserContact.getPhone());
        // int after = contactsPage.getContactsListSize();
        Assert.assertEquals(count, 1);
        System.out.println(count);
    }

    @Test
    public void testUserAlreadyExistRegistration() throws IOException {

        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");

        String email = EmailGenerator.generateEmail(10, 5, 3);
        String password = PasswordStringGenerator.generateString();
        PropertiesWriter.writeProperties("existingUserEmail", email, true);
        PropertiesWriter.writeProperties("existingUserPassword", password, false);

        loginPage.fillEmailField(email)
                .fillPasswordField(password)
                .clickByRegistartionButton();
        Allure.step("Step 3");
//        Assert.assertTrue(new ContactsPage(getDriver()).isElementPresent());
//        TakeScreen.takeScreenshot("screenreg");
        ContactsPage contact = new ContactsPage(getDriver());
        loginPage = contact.logOut();

        String expectedString = "User already exist";
        Alert alert = loginPage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByRegistartionButton();
        boolean isAlertHandledt = AlertHandler.handAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandledt);


    }

    @Test
    public void testUserAlreadyExistRegistrationUser() throws IOException {
        String filename = "userDataFile.ser";
        String expectedString = "User already exist";

        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        User user = new User(EmailGenerator
                .generateEmail(10, 5, 3), PasswordStringGenerator
                .generateString());

        loginPage.fillEmailField(user.getUserEmail())
                .fillPasswordField(user.getUserPassword())
                .clickByRegistartionButton();

        User.serializationUser(user, filename);

        ContactsPage contact = new ContactsPage(getDriver());
        loginPage = contact.logOut();

        User userDesirealizationUser = User.deserializationUser(filename);

        Alert alert = loginPage.fillEmailField(userDesirealizationUser.getUserEmail())
                .fillPasswordField(userDesirealizationUser.getUserPassword())
                .clickByRegistartionButton();

        boolean isAlertHandledt = AlertHandler.handAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandledt);

    }

}

