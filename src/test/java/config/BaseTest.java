package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    private WebDriver driver;


    public  WebDriver getDriver() {
        return driver;
    }
    private void initializeDriver(String browser){
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.accept_languages", "en");
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Invalid browser " + browser);
        }
    }
    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        initializeDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeGroups(groups = {"group1"})
    @Parameters("browser")
    public void setUpForGroup1(@Optional() String browser) {
        initializeDriver(browser);
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //BasePage.setDriver(driver);
    }
    @AfterGroups(groups = {"group1"})
    public void tearDownGroup() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();

            //driverThreadLocal.remove();
        }
    }


}


/*
public class BaseTest {
    private static final ThreadLocal<WebDriver> driverThreadLocal =
            new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
//          WebDriverManager.chromedriver().browserVersion("122.0.6261.69").setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");
            //options.addArguments("--headless");
            driverThreadLocal.set(new ChromeDriver(options));

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.assert_language", "en");
            driverThreadLocal.set(new FirefoxDriver(options));
        } else if (browser.equalsIgnoreCase("safari")) {
            SafariOptions options = new SafariOptions();
            options.setCapability("language", "en");
            driverThreadLocal.set(new SafariDriver());

        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
        BasePage.setDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver(); // Получаем текущий экземпляр WebDriver с помощью метода getDriver().
        if (driver != null) { // Проверяем, что экземпляр драйвера не равен null.
          //  driver.quit(); // Если драйвер не равен null, то закрываем браузер с помощью метода quit().
            driverThreadLocal.remove(); // Удаляем текущий экземпляр WebDriver из объекта driverThreadLocal.
        }
    }
}
*/
