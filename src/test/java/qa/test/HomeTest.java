package qa.test;

import org.testng.Assert;
import org.testng.annotations.*;
import qa.base.BaseClass;
import qa.pages.HomePage;
import qa.pages.WarpPage;

public class HomeTest extends BaseClass {

    HomePage homePage;
    WarpPage warpPage;

    @BeforeMethod
    public void beforeMethod() {
        homePage = new HomePage();
        warpPage = new WarpPage();
    }

    @Test(priority = 1)
    public void ScenarioOneTest() {
        warpPage = homePage.clickWrap();

        // Verify page title
        String actualPageTitle = warpPage.warpPageGetTitle();
        String expectedPageTitle = "Warp";
        Assert.assertEquals(actualPageTitle, expectedPageTitle);

//        warpPage.clickOnItem("Nigeria");






    }



}
