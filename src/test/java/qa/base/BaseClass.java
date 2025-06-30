package qa.base;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import qa.utils.TestUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {

    protected static AppiumDriver driver;
    protected static Properties props;
    protected InputStream inputStream;

    @BeforeTest
    public void beforeTest() {


        try {
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream == null) throw new RuntimeException("config.properties not found");
            props.load(inputStream);
            inputStream.close();

            String appPath = System.getProperty("user.dir") + File.separator + props.getProperty("androidAppLocation");

            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("Small Phone")
                    .setApp(appPath);

            URL url = new URL(props.getProperty("appiumURL"));
            driver = new AndroidDriver(url, options);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Appium Driver: " + e.getMessage(), e);
        }
    }


    public AppiumDriver getDriver(){
        return driver;
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }


    public void dragAndDropElement(WebElement source, WebElement target) {
        driver.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) source).getId(),
                "endX", target.getLocation().getX(),
                "endY", target.getLocation().getY()
        ));
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
