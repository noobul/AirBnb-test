package com.automation.tests;

import com.automation.TestBase;
import com.automation.pageobjects.TestItems;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class TestTests extends TestBase {

    TestItems testItems;
    private SoftAssert softAssert = new SoftAssert();


    @BeforeMethod
    public void initializePages(){
        testItems = new TestItems(driver);
    }

    @Test
    public void verifyResultsBasedOnSearchCriteria() throws InterruptedException, IOException {
        int daysCheckIn = 7;
        int daysCheckOut = 14;
        String city = "Rome";
        String country = "Italy";
        String location = city +", " + country;
        int adults = 2;
        int children = 1;

        testItems.setWhereField(location);
        testItems.setCheckInDate(daysCheckIn);
        testItems.setCheckOutDate(daysCheckOut);
        testItems.addGuestsField();
        testItems.setAddAdult(adults);
        testItems.setAddChild(children);
        testItems.closeGuestsField();
        testItems.submitHomePage();
        softAssert.assertTrue(testItems.isSearchQueryTheSame(city), "City name.");
        softAssert.assertTrue(testItems.isGuestNumberTheSame(adults + children), "Amount of guests.");
        softAssert.assertTrue(testItems.isDateIntervalTheSame(daysCheckIn, daysCheckOut), "Date interval.");
        softAssert.assertTrue(testItems.areEnoughApartmentGuests(adults + children), "Guests per apartment. ");
        softAssert.assertAll();
    }

    @Test
    public void verifyResultsAndDetailsMatchExtraFilters() throws InterruptedException {
        int daysCheckIn = 7;
        int daysCheckOut = 14;
        String city = "Rome";
        String country = "Italy";
        String location = city +", " + country;
        int adults = 2;
        int children = 1;

        testItems.setWhereField(location);
        testItems.setCheckInDate(daysCheckIn);
        testItems.setCheckOutDate(daysCheckOut);
        testItems.addGuestsField();
        testItems.setAddAdult(adults);
        testItems.setAddChild(children);
        testItems.closeGuestsField();
        testItems.submitHomePage();
    }

    @Test
    public void verifyPropertyIsDisplayedOnMapCorectly() throws InterruptedException {
        int daysCheckIn = 7;
        int daysCheckOut = 14;
        String city = "Rome";
        String country = "Italy";
        String location = city +", " + country;
        int adults = 2;
        int children = 1;

        testItems.setWhereField(location);
        testItems.setCheckInDate(daysCheckIn);
        testItems.setCheckOutDate(daysCheckOut);
        testItems.addGuestsField();
        testItems.setAddAdult(adults);
        testItems.setAddChild(children);
        testItems.closeGuestsField();
        testItems.submitHomePage();
    }
}
