package com.automation.pageobjects;

import com.automation.TestBase;
import com.automation.helpers.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Calendar;
import java.util.Formatter;


public class TestItems extends TestBase {

    WebDriver driver;

    public TestItems(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='Koan-magic-carpet-koan-search-bar__input']")
    private WebElement whereField;

    @FindBy(xpath = "//*[@aria-label=Calendar']")
    private WebElement calendar;

    @FindBy(xpath = "//*[@id='checkout_input']")
    private WebElement checkOutField;

    @FindBy(xpath = "//*[@id='FMP-target']/div/div[1]/div/div/div/div/div/div[2]/div/form/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]/div[1]/div[2]")
    private WebElement nexMonthButtonCheckIn;

    @FindBy(xpath = "//*[@aria-label = 'Move forward to switch to the next month.']")
    private WebElement nextMonthButtonCheckOut;

    public void setWhereField(String where){
        waitForElementToBeVisible(whereField);
        sendKeys(whereField, where);
        whereField.sendKeys(Keys.RETURN);
    }

    public String getMonthLater(int days){
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        fmt.format("%tB", cal);
        String month = fmt.toString();
        return month;
    }
    public String getMonth(){
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt.format("%tB", cal);
        String month = fmt.toString();
        return month;
    }

    public String getDayLater(int days){
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        fmt.format("%te", cal);
        String day = fmt.toString();
        return day;
    }

    public void setCheckInDate(int days){
        //waitForElementToBeVisible(calendar, FIVE_SEC_WAIT);
        //WebElement month = driver.findElement(By.xpath("//*/strong[contains(text() , '"+ getMonthLater(days) +"')]"));
        if(getMonth().equals(getMonthLater(days)) == false){
            nexMonthButtonCheckIn.click();
        }
        WebElement day = driver.findElement(By.xpath("//*/td[contains(@aria-label,'"+ getMonthLater(days) +"') and contains(@aria-label, '" + getDayLater(days) + "')]"));
        waitForElementToBeClickable(day);
        day.click();
    }

    public void setCheckOutDate(int days){
        //waitForElementToBeVisible(calendar, FIVE_SEC_WAIT);
        //WebElement month = driver.findElement(By.xpath("//*/strong[contains(text() , '"+ getMonthLater(days) +"')]"));
        if(getMonth().equals(getMonthLater(days)) == false){
            nextMonthButtonCheckOut.click();
        }
        WebElement day = driver.findElement(By.xpath("//*/td[contains(@aria-label,'"+ getMonthLater(days) +"') and contains(@aria-label, '" + getDayLater(days) + "')]"));
        waitForElementToBeClickable(day);
        day.click();
    }
}
