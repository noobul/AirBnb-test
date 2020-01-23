package com.automation;

import com.automation.helpers.CaptureScreenShot;
import com.automation.helpers.ExtendManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.automation.helpers.Configuration;
import com.automation.helpers.Configuration.*;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TestBase extends ExtendManager {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest logger;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy_hh:mm");

    @BeforeTest
    public void setup(){
        startBrowser();
        maximize();
        goToUrl(Configuration.TEST_URL);
        waitPageLoad(TimeOuts.FIVE_SEC_WAIT);
        extent = ExtendManager.getInstance("live");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable.gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    }

    @BeforeMethod
    public void beforeMethrod(Method method){
        System.out.println("TEST STARTED: " + method.getName());;
        logger = extent.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
        public void afterMethod(ITestResult result) throws IOException {
            if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("FAILED: " + result.getName());
                System.out.println(result.getThrowable().toString());
                logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().toString(), ExtentColor.RED));
                if (driver != null) {
                    logger.addScreenCaptureFromPath(CaptureScreenShot.captureScreen(driver, CaptureScreenShot.generateFileName(result)));
                    driver.manage().deleteAllCookies();
                    // in case of failure the browser needs to be restarted, otherwise the next test will crash
                    driver.quit();
                    startBrowser();
                    maximize();
                    goToUrl(Configuration.TEST_URL);
                    waitPageLoad(Configuration.TimeOuts.FIVE_SEC_WAIT);
                }
            }

            else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("SKIPPED: ");
                logger.log(Status.SKIP, MarkupHelper.createLabel(result.getThrowable().toString(), ExtentColor.ORANGE));
                // in case of failure the browser needs to be restarted, otherwise the next test will crash
                driver.quit();
                startBrowser();
                maximize();
                goToUrl(Configuration.TEST_URL);
                waitPageLoad(TimeOuts.FIVE_SEC_WAIT);
            }

            else {
                System.out.println("PASSED: " + result.getName());
                logger.log(Status.PASS, MarkupHelper.createLabel("Passed", ExtentColor.GREEN));
                driver.quit();
                startBrowser();
                maximize();
                goToUrl(Configuration.TEST_URL);
                waitPageLoad(TimeOuts.FIVE_SEC_WAIT);
            }

            extent.flush();
        }

    @AfterTest
    public void quitDriver(){
        if(driver != null)
            driver.quit();
    }

    public static void startBrowser() {
        final String CHROME_DRIVER = "src\\main\\resources\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver = new ChromeDriver();
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void logReport(String logType, String logDetails) {
        switch (logType) {
            case LogType.PASS:
                logger.log(Status.PASS, logDetails);
                break;
            case LogType.FAIL:
                logger.log(Status.FAIL, logDetails);
                break;
            case LogType.WARNING:
                logger.log(Status.WARNING, logDetails);
                break;
            case LogType.ERROR:
                logger.log(Status.ERROR, logDetails);
                break;
            case LogType.INFO:
                logger.log(Status.INFO, logDetails);
                break;
        }
    }

    public boolean checkPointBoolCondition(boolean condition, String passMsg, String failMsg) throws SecurityException, IOException {
        Boolean flag = true;

        if (condition)
            logReport(LogType.PASS, passMsg);
        else {
            logReport(LogType.FAIL, failMsg + takeScreenShot());
            flag = false;}

        return flag;
    }

    public ExtentTest takeScreenShot() throws SecurityException, IOException {
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        return logger.addScreenCaptureFromPath(CaptureScreenShot
                .captureScreen(driver, CaptureScreenShot
                        .generateFileName(stack[stack.length -25].getMethodName())));
    }

    public void sendKeys(WebElement element, String text) {
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void scrollInToViewAndClick(WebElement element){
        waitForElementToBeVisible(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        click(element);
    }

    public void scrollInToView(WebElement element) throws InterruptedException {
        waitForElementToBeVisible(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    public void switcTab(int tabPosition){
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());;
        driver.switchTo().window(tabs.get(tabPosition));
    }

    public void hoverOverElement(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public String getAttributeText(WebElement element, String attributeName){
        waitForElementToBeVisible(element);
        String attName = element.getAttribute(attributeName);
        return attName;
    }

    // Waits
    public void waitPageLoad(int timePageLoad) {
        driver.manage().timeouts().pageLoadTimeout(timePageLoad, TimeUnit.SECONDS);
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return (new WebDriverWait (driver, TimeOuts.THIRTY_SEC_WAIT)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return (new WebDriverWait (driver, TimeOuts.SIXTY_SEC_WAIT)).until(ExpectedConditions.visibilityOf(element));
    }
}
