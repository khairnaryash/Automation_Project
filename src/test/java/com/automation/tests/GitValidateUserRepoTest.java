package com.automation.tests;

import com.automation.reporter.ExtentReporter;
import com.automation.reporter.customAssert.CustomAssert;
import com.automation.reporter.customAssert.CustomSoftAssert;
import com.automation.services.GitHubServices;
import com.automation.pageObjects.github.UserRepositoriesPage;
import com.automation.reporter.Log;
import com.automation.utils.BaseSeleniumTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GitValidateUserRepoTest extends BaseSeleniumTest {


    @DataProvider(name = "simpleDataProvider")
    public Object[][] dataProvider() {
        return getDataFromTestDataFile("Github.xls", "Data");
    }

    @Test(dataProvider = "simpleDataProvider")
    public void test(HashMap<String, String> testData) {

        Log.info("Starting test case  " + testData.get("TestCaseName"));
        driver.get(getProperty("github_url") + "/" + testData.get("UserName"));
        UserRepositoriesPage userRepositoriesPage = new UserRepositoriesPage(driver);

        Log.info("Title : " + userRepositoriesPage.getTitle());

        CustomAssert.assertTrue(userRepositoriesPage.validateUserName(testData.get("UserName")), "User repositories page open");

        List<String> actualList = userRepositoriesPage.getUserPublicRepoList();

        Collections.sort(actualList);

        GitHubServices services = new GitHubServices(getProperty(testData.get("ServiceType") + ".baseUrl"));

        List<String> expectedlList = services.getUserRepo(testData);
        Collections.sort(expectedlList);

        CustomAssert.assertEquals(actualList.size(), expectedlList.size(), "Repositories size");
        CustomSoftAssert softAssert = new CustomSoftAssert();
        ExtentReporter.getTest().log(Status.INFO, "Validating repo name : ");
        for (int i = 0; i < actualList.size(); i++)
            softAssert.assertEquals(actualList.get(i), expectedlList.get(i));

        softAssert.assertAll();
    }

    @BeforeMethod
    public void loadApiProperties() {
        loadProperties(String.format("config/%s/api.properties", environment));
    }


}
