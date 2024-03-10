package helpers;

import com.google.common.io.ByteSource;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static config.BaseTest.getDriver;

public class TakeScreen {
    @Attachment(value ="Failure screenshot", type = "image/png" )
    public static byte[] takeScreenshot(String link)  {
       String screenshotName=link+"_"+System.currentTimeMillis()+".png";
        File tmp = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        File screenshot=new File("src/test/screenshots/"+screenshotName);

        try {
            FileUtils.copyFile(tmp, screenshot);
            return Files.readAllBytes(Paths.get("screenshots//"+screenshotName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

