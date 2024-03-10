package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import model.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.util.Random;

public class PhoneBookTest extends BaseTest {
    @Test(description = "The test checks the empty field warning declaration.")
    public void registrationWithoutPassword()  throws InterruptedException {
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
        Thread.sleep(5000);
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
        AddPage addPage=new AddPage(getDriver());
        Contact contact =new Contact(NameAndLastNameGenerator.generateName()
                ,NameAndLastNameGenerator.generateLastName()
                ,PhoneNumberGenerator.generatePhoneNumber()
                ,EmailGenerator.generateEmail(10,5,3)
                ,AddressGenerator.generateAddress()
                ,"New contact");
        //System.out.println(contact.toString());

        addPage.fillFormAndSave(contact);
        ContactsPage contactsPage=new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(contact));
        TakeScreen.takeScreenshot("screen");
        Thread.sleep(3000);


    }

    @Test
    public void testSuccessfullyRegistration(){

        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        loginPage.fillEmailField(EmailGenerator.generateEmail(10,5,3))
                .fillPasswordField(PasswordStringGenerator.generateString())
                .clickByRegistartionButton();
        Allure.step("Step 3");
        Assert.assertTrue(new ContactsPage().isElementPresent(By.xpath("//button")));
        TakeScreen.takeScreenshot("screenreg");
    }

}
