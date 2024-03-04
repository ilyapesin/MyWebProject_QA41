package tests;

import config.BaseTest;
import helpers.TopMenuItem;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.util.Random;

public class PhoneBookTest extends BaseTest {
    @Test
    public void phoneBookTest_001() throws InterruptedException {

        int i = new Random().nextInt(1000) + 1000;
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField("vasya_pupkingmail.com")
                .fillPasswordField("Vp12345$")
                .clickByLoginButton();

        Thread.sleep(5000);
    }

}
