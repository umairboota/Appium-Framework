package qa.pages;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import qa.base.BaseClass;

public class WarpPage extends BaseClass {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Warp']")
    private WebElement pageTitle;

    @AndroidFindBy(id = "android:id/list")
    private WebElement listView;

    public WarpPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public String warpPageGetTitle() {
        return getAttribute(pageTitle, "text");
    }

    //  Dynamic locator method (reusable)
    public By getListItemLocator(String visibleText) {
        return By.xpath("//android.widget.TextView[@resource-id='com.mobeta.android.demodslv:id/text' and @text='" + visibleText + "']");
    }

    //  Scroll and click using dynamic locator
    public void clickOnItem(String visibleText) {
        By locator = getListItemLocator(visibleText);

        while (driver.findElements(locator).isEmpty()) {
            driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) listView).getId(),
                    "direction", "up",
                    "percent", 0.50
            ));
        }

        driver.findElement(locator).click();
    }


}
