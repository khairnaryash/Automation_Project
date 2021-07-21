package com.automation.tests;

import com.automation.pageObjects.WikiHomePage;
import com.automation.reporter.customAssert.CustomAssert;
import com.automation.reporter.Log;
import com.automation.utils.BaseSeleniumTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class SampleWikiSearchTest extends BaseSeleniumTest {

    @DataProvider(name = "simpleDataProvider")
    public Object[][] dataProvider() {
        return getDataFromTestDataFile("wiki_sample.xls", "Data");
    }

    @Test(dataProvider = "simpleDataProvider")
    public void test(HashMap<String, String> testData) {

        Log.info("Starting test case  " + testData.get("TestCaseName"));
        driver.get(getProperty("wikipedia_url"));
        WikiHomePage homePage = new WikiHomePage(driver);

        Log.info("Title : " + homePage.getTitle());

        CustomAssert.assertTrue(homePage.isDisplayed(), "Wiki home page loaded");

        homePage.enterTextInSearch(testData.get("SearchText"));
        homePage.clickSearch();
        CustomAssert.assertEquals(homePage.getTitle(), testData.get("PageTitle").trim(),"Page title validation");
    }


}


