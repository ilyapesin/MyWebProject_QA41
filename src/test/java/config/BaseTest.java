package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BasePage;

import java.time.Duration;

public class BaseTest {
    private static final ThreadLocal<WebDriver> driverThreadLocal =
            new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser) {
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
            // driver.quit(); // Если драйвер не равен null, то закрываем браузер с помощью метода quit().
            driverThreadLocal.remove(); // Удаляем текущий экземпляр WebDriver из объекта driverThreadLocal.
        }
    }
}
