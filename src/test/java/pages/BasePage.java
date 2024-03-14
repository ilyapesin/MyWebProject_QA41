package pages;

import helpers.TopMenuItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import java.util.List;

public class BasePage {
    protected static WebDriver driver;
    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }


}
