package tests;

import config.BaseTest;
import helpers.TopMenuItem;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

import java.util.Random;

public class PhoneBookTest extends BaseTest {
    @Test
    public void phoneBookTest_001() throws InterruptedException {

        int i = new Random().nextInt(1000) + 1000;
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        loginPage.fillEmailField("vasya_pupkin" + i + "@gmail.com")
                .fillPasswordField("Vp12345$")
                .clickByRegistartionButton();

        Thread.sleep(5000);
    }
}
