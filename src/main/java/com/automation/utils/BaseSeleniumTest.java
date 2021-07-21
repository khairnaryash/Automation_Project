package com.automation.utils;

import com.automation.Exceptions.InvalidValueException;
import com.automation.enums.SupportedBrowser;
import com.automation.reporter.Log;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;


public class BaseSeleniumTest extends BaseTest {
    protected WebDriver driver;


    @Parameters({"browser"})
    @BeforeMethod
    public void browserSetup(String browserName) {

        SupportedBrowser browser;

        try {
            browser = SupportedBrowser.valueOf(browserName.toUpperCase());
        } catch (Exception e) {
            Log.error("********** Invalid browser selected ***********");
            Log.info("Supported browsers : ");
            for (SupportedBrowser b : SupportedBrowser.values()) {
                Log.info(b.name());
            }

            throw new InvalidValueException("Invalid browser value :" + browserName);
            //			System.exit(0);

        }

        String version = getProperty(String.format("selenium.%s.version", browser.toString().toLowerCase().replace("_headless", "")));
        Log.info(String.format("Initializing Browser : %s , driver version : %s", browser, version));

        SeleniumDriverManager.initializeDriver(browser, version);
        driver = SeleniumDriverManager.getWebDriver();
        try {
            Long.valueOf(getOptionalProperty("selenium.implicitwait", "10"));
        } catch (Exception e) {
            throw new InvalidValueException("Please provide valid value for selenium.implicitwait");
        }
        driver.manage().timeouts().implicitlyWait(Long.valueOf(getOptionalProperty("selenium.implicitwait", "10")), TimeUnit.SECONDS);
    }

    @AfterMethod
    public void afterMethod(ITestContext testContext) {
        Log.info("Test Method Completed :" + testContext.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownAfterTest() {
        Log.info("********* Closing Browser *********");
        driver.quit();
    }

    @Parameters({"environment"})
    @BeforeClass
    public void testSetup2(String environment_) {
        environment = environment_;
        Log.info("environment: " + environment);
        loadProperties(String.format("config/%s/selenium.properties", environment));

    }


}
