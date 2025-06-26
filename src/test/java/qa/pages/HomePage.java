package qa.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import qa.base.BaseClass;

public class HomePage extends BaseClass {

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.mobeta.android.demodslv:id/activity_title\" and @text=\"Warp\"]")
    private WebElement wrapField;




    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public WarpPage clickWrap(){
        click(wrapField);
        return new WarpPage();
    }


}
