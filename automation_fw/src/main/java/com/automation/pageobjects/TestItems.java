package com.automation.pageobjects;

import com.automation.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.swing.*;
import javax.xml.xpath.XPathExpression;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @FindBy(xpath = "//*[@id=\"menuItemButton-dynamicMoreFilters\"]/button")
    private WebElement moreFiltersButton;

    @FindBy(xpath = "//*[@id=\"filterItem-stepper-min_bedrooms-0\"]/button[2]")
    private WebElement addBedroomMoreFilters;

    @FindBy(xpath = "//*/div[@aria-label = 'Extras']//span[contains(text(), 'Show all')]")
    private WebElement moreFiltersExtrasExpand;

    @FindBy(xpath = "//*/div[@aria-label = \"Facilities\"]/div/div[*]/label/span[*]/div[contains(text(), \"Pool\")]")
    private WebElement poolUnderFacilities;

    @FindBy(xpath = "//*/label[contains(@for, \"filterItem-checkbox-amenities-\")]/span[2]/div[contains(text(), \"Pool\")]")
    private WebElement poolUnderAmenities;

    @FindBy(xpath = "//*/div[@aria-label =\"More filters\"]/footer/button")
    private WebElement moreFiltersShowButton;

    @FindBy(xpath = "//*/div[1]/div[1]/div[1]/div[1]/div[*]/a[@data-check-info-section = \"true\"]")
    private WebElement firstResult;

    /*public void switchToIFrame(WebElement element){
        driver.switchTo().frame(element);
    }*/

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

    public void setDate(){

    }

    public void addGuestsField(){
        click(guestsField);
    }

    public void clickMoreFiltersShowButton() throws InterruptedException {
        click(moreFiltersShowButton);
        Thread.sleep(3000);
    }


    public void clickMoreFilters(){
        waitForElementToBeClickable(moreFiltersButton);
        click(moreFiltersButton);
    }

    public void clickFirstResult() throws InterruptedException {
        waitForElementToBeVisible(firstResult);
        click(firstResult);
        Thread.sleep(500);
    }

    public void selectPoolOption(){
        try{
            click(poolUnderFacilities);
        }catch (Exception e){
            click(moreFiltersExtrasExpand);
            click(poolUnderAmenities);
        }
    }

    //clicks a webElement multiple times
    public void clickAddButton(int guestNumber, WebElement guestType) throws InterruptedException {
        for(int i = 0; i < guestNumber; i++){
            click(guestType);
            Thread.sleep(1000);
        }
    }

    public void setAddAdult(int adultNumber) throws InterruptedException {
        clickAddButton(adultNumber, addAdult);
    }

    public void setAddChild(int childNumber) throws InterruptedException {
        clickAddButton(childNumber, addChild);
    }

    public void setAddBedroomsMoreFilters(int bedroomsNumber) throws InterruptedException {
        clickAddButton(bedroomsNumber, addBedroomMoreFilters);
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

    public boolean isItemUnderAmenities(String amenities) throws IOException, InterruptedException {
        Thread.sleep(3000);
        //Actions action = new Actions(driver);
        //action.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).perform();
        scrollInToView(driver.findElement(By.xpath("//*[@id=\"amenities\"]")));
        boolean amenity = driver.findElement(By.xpath("//*[@id=\"amenities\"]//div[contains(text(), '"+amenities+"')]")).isDisplayed();
        return checkPointBoolCondition(amenity, ""+amenities+" are present.", ""+amenities+" are not present." );
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

    public int listingDetailScrubber(int accommodation, String accommodationName) throws IOException {
        List<WebElement> propertyDetailsListElements = driver.findElements(By.xpath("//*/div[*]/div[1]/div[1]/div[1]/div[*]/a[@data-check-info-section = \"true\"]/../div[2]/div[3]"));
        List<String> propertyDetailsList = new ArrayList<>();
        List<Integer> numberOfXList =  new ArrayList<>();
        int i = 0;

        for(WebElement propertyDetailsElement : propertyDetailsListElements){
            propertyDetailsList.add(propertyDetailsElement.getText());
        }

        for(String accommodations : propertyDetailsList){
            Pattern p = Pattern.compile("(\\d+).(?:"+accommodationName+")");
            Matcher m = p.matcher(accommodations);
            if(m.find()) {
                String s = m.group(1);
                numberOfXList.add(Integer.parseInt(s));
            }
        }

        for(int xInList : numberOfXList){
            if (xInList >= accommodation){
                i = 1;
            }else{
                i = 0;
            }
        }
        return i;
    }

    public boolean areEnoughApartmentGuests(int guests) throws IOException {
        int i = listingDetailScrubber(guests, "guests");
        return checkPointBoolCondition(i == 1, ""+guests+" or more guest can be accommodated.", "Guest that can be accommodated is wrong.");
    }

    public boolean areEnoughBedrooms(int bedrooms) throws IOException {
        int i = listingDetailScrubber(bedrooms,"bedrooms");
        return checkPointBoolCondition(i == 1, ""+bedrooms+" or more rooms are available.", "Rooms available is wrong.");
    }
}
