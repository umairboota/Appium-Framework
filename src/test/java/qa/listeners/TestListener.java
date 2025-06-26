package qa.listeners;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import qa.base.BaseClass;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport_" + timestamp + ".html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project", "Mobile Automation");
        extent.setSystemInfo("Tester", "Umair Boota");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Test failed: " + result.getThrowable());

        try {
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            test.get().addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            test.get().fail("Failed to attach screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String captureScreenshot(String methodName) throws IOException {
        BaseClass base = new BaseClass();
        File src = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.FILE);
        String dest = "test-output/screenshots/" + methodName + "_" + new Date().getTime() + ".png";
        FileUtils.copyFile(src, new File(dest));
        return dest;
    }
}
