package helpers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class AlertHandler {
    WebDriver driver;

    public AlertHandler(WebDriver driver) {
        this.driver = driver;
    }
    public static boolean handAlert(Alert alert, String expectedText) {
        if(alert!=null) {
            String alertText = alert.getText();
            System.out.println("ALERT_TEXT "+alertText);
            alert.accept();  // Принимаем всплывающее окно, нажимая кнопку "OK"
            return alertText.contains(expectedText); // Возвращает true, если текст в всплывающем окне содержит ожидаемый текст (expectedText),
            // в противном случае возвращает false.
        }
        return false;
        }

}

