package com.automation.utils;

import com.automation.Exceptions.InvalidValueException;
import com.automation.enums.SupportedBrowser;
import com.automation.reporter.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

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

        Log.info("Initializing Browser : " + browser);
        String version = getProperty(String.format("selenium.%s.version", browser.toString().toLowerCase().replace("_headless", "")));
        SeleniumDriverManager.initializeDriver(browser, version);
        driver = SeleniumDriverManager.getWebDriver();

    }

    @AfterMethod
    public void afterMethod(Method method) {

        Log.info("Test Method Completed :" + method.getName());
        CommonHelper.takeScreenShot(driver, "TearDown_" + method.getName());

    }

    @AfterMethod
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
