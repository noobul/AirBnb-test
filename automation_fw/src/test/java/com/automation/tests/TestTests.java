package com.automation.tests;

import com.automation.TestBase;
import com.automation.pageobjects.TestItems;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestTests extends TestBase {

    TestItems testItems;

    @BeforeMethod
    public void initializePages(){
        testItems = new TestItems(driver);
    }

    @Test
    public void verifyResultsBasedOnSearcCriteria(){
        int daysCheckIn = 7;
        int daysCheckOut = 14;

        testItems.setWhereField("Rome, Italy");
        testItems.setCheckInDate(daysCheckIn);
        testItems.setCheckOutDate(daysCheckOut);
        System.out.println("fdjk");

    }
}
