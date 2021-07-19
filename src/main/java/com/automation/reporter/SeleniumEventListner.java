package com.automation.reporter;

import com.automation.utils.CommonHelper;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Arrays;

public class SeleniumEventListner implements WebDriverEventListener {
    @Override
    public void beforeAlertAccept(WebDriver driver) {
        ExtentReporter.getTest().log(Status.PASS, "Accepting alert");
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        ExtentReporter.getTest().log(Status.PASS, "Navigating to URL : " + url);
        Log.info("Navigating to URL : " + url);
//        ExtentReporter.test.log(Status.INFO," Info Navigating to URL : " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        String path = addScreenShot(driver, "navigated_to" + driver.getTitle().replaceAll("[^0-9a-zA-Z:,]",""));
        try {
            ExtentReporter.getTest().log(Status.PASS, "navigated_to: " + driver.getTitle() + " " + path);
        } catch (Exception e) {
        }
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        String path = addScreenShot(driver, "Clicked_on_Element" + element.hashCode());
        try {
            ExtentReporter.getTest().log(Status.PASS, "Clicked on element" + path);
        } catch (Exception e) {
        }
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        if (keysToSend != null && keysToSend.length > 0) {
            String path = addScreenShot(driver, "entering_text_" + element.hashCode());
            try {
                ExtentReporter.getTest().log(Status.PASS, "Entering text : " + Arrays.toString(keysToSend) + path);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {

    }

    private String addScreenShot(WebDriver driver, String name) {
        String temp = "";
        String imagePath = CommonHelper.takeScreenShot2(driver, name);
        if (null != imagePath) {
            temp = "<a onclick='this.href=this.childNodes[0].src' data-featherlight='image'>" + "<img src=\""
                    + imagePath + "\" width=100 height=60></a>";

        }
        return temp;

    }
}
