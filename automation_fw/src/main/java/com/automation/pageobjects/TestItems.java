package com.automation.pageobjects;

import com.automation.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;


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

    @FindBy(xpath = "//*[@id='lp-guestpicker']")
    private WebElement guestsField;

    @FindBy(xpath = "//*[@id='lp-guestpicker' and @aria-expanded='true']")
    private WebElement guestsFieldExpanded;

    @FindBy(xpath = "//*/div[contains(@aria-labelledby, 'adults')]/div/div[2]/div/div[3]/button")
    private WebElement addAdult;

    @FindBy(xpath = "//*/div[contains(@aria-labelledby, 'children')]/div/div[2]/div/div[3]/button")
    private WebElement addChild;

    @FindBy (xpath = "//*/button[@type = 'submit']/span[contains(text(), 'Search')]")
    private WebElement homePageSearch;

    @FindBy (xpath = "//*[@aria-label = 'Search']")
    private WebElement searchField;

    @FindBy (xpath = "//*[@id=\"menuItemButton-guest_picker\"]/button/span[2]/span")
    private WebElement guestNumberAfterSearch;

    @FindBy (xpath = "//*[@id=\"menuItemButton-date_picker\"]/button/span[2]/span")
    private WebElement dateIntervalAfterSearch;

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
        if(getMonth().equals(getMonthLater(days)) == false){
            nexMonthButtonCheckIn.click();
        }
        WebElement day = driver.findElement(By.xpath("//*/td[contains(@aria-label,'"+ getMonthLater(days) +"') and contains(@aria-label, '" + getDayLater(days) + "')]"));
        waitForElementToBeClickable(day);
        day.click();
    }

    public void setCheckOutDate(int days){
        if(getMonth().equals(getMonthLater(days)) == false){
            nextMonthButtonCheckOut.click();
        }
        WebElement day = driver.findElement(By.xpath("//*/td[contains(@aria-label,'"+ getMonthLater(days) +"') and contains(@aria-label, '" + getDayLater(days) + "')]"));
        waitForElementToBeClickable(day);
        day.click();
    }

    public void addGuestsField(){
        click(guestsField);
    }

    public void addGuests(int guestNumber, WebElement guestType) throws InterruptedException {
        for(int i = 0; i < guestNumber; i++){
            click(guestType);
            Thread.sleep(1000);
        }
    }

    public void setAddAdult(int adultNumber) throws InterruptedException {
        addGuests(adultNumber, addAdult);
    }

    public void setAddChild(int childNumber) throws InterruptedException {
        addGuests(childNumber, addChild);
    }

    public void submitHomePage(){
        click(homePageSearch);
    }

    public void closeGuestsField(){
        click(guestsFieldExpanded);
    }

    public String getSearchFieldValue(){
        String searchFieldValue = searchField.getAttribute("value");
        return searchFieldValue;
    }

    public boolean isSearchQueryTheSame(String city) throws IOException, InterruptedException {
        Thread.sleep(3000);
        return checkPointBoolCondition(getSearchFieldValue().contains(city), ""+city+" is correct. ", ""+city+" is incorrect. ");
    }

    public String getGuestNumber(){
        String guestNumber = guestNumberAfterSearch.getAttribute("aria-label");
        return guestNumber;
    }

    public boolean isGuestNumberTheSame(int guestNumber) throws InterruptedException, IOException {
        Thread.sleep(3000);
        String guestNumberString = String.valueOf(guestNumber);
        return checkPointBoolCondition(getGuestNumber().contains(guestNumberString), ""+guestNumberString+" is the amount of guests.", ""+guestNumberString+" is not the amount of guests.");
    }

    public String getDateInterval(){
        String dateInterval = dateIntervalAfterSearch.getAttribute("aria-label");
        return dateInterval;
    }

    public boolean isDateIntervalTheSame(int checkin, int checkout) throws InterruptedException, IOException {
        Thread.sleep(3000);

        String checkinDayString = String.valueOf(getDayLater(checkin));
        String checkoutDayString = String.valueOf(getDayLater(checkout));
        String checkinMonthShort = getMonthLater(checkin).substring(0, 3);
        String checkoutMonthShort = getMonthLater(checkout).substring(0, 3);
        String dateIntervalShort = ""+checkinMonthShort+" "+checkinDayString+" - "+checkoutMonthShort+" "+checkoutDayString+"";

        return checkPointBoolCondition(getDateInterval().contains(dateIntervalShort), ""+dateIntervalShort+" is the correct interval.", ""+dateIntervalShort+" is not the correct interval.");
    }

    public boolean areEnoughApartmentGuests(int guests) throws IOException {
        List<WebElement> propertyDetailsListElements = driver.findElements(By.xpath("//*/div[*]/div[1]/div[1]/div[1]/div[*]/a[@data-check-info-section = \"true\"]/../div[2]/div[3]"));
        List<String> propertyDetailsList = new ArrayList<>();
        List<Integer> numberOfGuestsList =  new ArrayList<>();
        int i = 0;

        for(WebElement propertyDetailsElement : propertyDetailsListElements){
            propertyDetailsList.add(propertyDetailsElement.getText());
        }

        for(String accommodations : propertyDetailsList){
            numberOfGuestsList.add(Integer.parseInt(accommodations.substring(0,1)));
        }

        for(int guestsInList : numberOfGuestsList){
            if (guestsInList >= guests){
                i = 1;
            }else{
                i = 0;
            }
        }
        return checkPointBoolCondition(i == 1, ""+guests+" or more can be accommodated.", "Guest that can be accommodated is wrong.");
    }
}
