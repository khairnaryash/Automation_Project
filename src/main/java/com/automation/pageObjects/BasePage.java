package com.automation.pageObjects;

import com.automation.reporter.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public abstract class BasePage {

    private WebDriver driver;

    public BasePage(WebDriver m_driver) {
        driver = m_driver;
    }

    public abstract boolean isDisplayed();

    public String getTitle() {
        return driver.getTitle().trim();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected WebElement getElementInPage(By by) {
        waitTillPageLoading(10);
        waitForElement(by,10);
        return driver.findElement(by);
    }

    protected List<WebElement> getElementsInPage(By by) {

        return driver.findElements(by);
    }

    protected void sendKeys(WebElement e, String text) {
        e.clear();
        e.sendKeys(text);
    }

    protected void clickElement(WebElement e) {
        e.click();
    }

    public void ClickUsingJS(WebElement element) {
        JavascriptExecutor Executor = (JavascriptExecutor) driver;
        Executor.executeScript("arguments[0].click();", element);
    }

    protected String getText(WebElement e) {
        return e.getText().trim();
    }

    protected String getText(By by) {
        waitTillPageLoading(10);
        waitForElement(by,10);
        return getText(getElementInPage(by));
    }

    public boolean isPageLoadingCompleted() {
        JavascriptExecutor Executor = (JavascriptExecutor) driver;
        return Executor.executeScript("return document.readyState").toString().equals("complete");

    }

    protected WebElement waitForElement(By by, int timeOutInSec) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSec))
                .pollingEvery(Duration.ofMillis(500));
        // .ignoring(NoSuchElementException.class); // either use try catch or use ignoring to ignore exception

        WebElement eleWithWait = wait.until(new Function<WebDriver, WebElement>() {

            public WebElement apply(WebDriver driver) {
                try {
                    WebElement e = driver.findElement(by);

                    return e;
                } catch (Exception e) {
                    return null;
                }
            }
        });

        return eleWithWait;
    }


    protected boolean waitForElementToBeVisible(By by, long timeOutInSec)  {

        boolean visible;

        visible = waitElementForExpectedCondition(timeOutInSec,
                ExpectedConditions.visibilityOfElementLocated(by));

        return visible;
    }

    protected boolean waitForElementToBeClickable(By by, long timeOutInSec) {

        boolean clickable;

        clickable = waitElementForExpectedCondition(timeOutInSec,
                ExpectedConditions.elementToBeClickable(by));

        return clickable;
    }


    protected boolean waitElementForExpectedCondition(long timeOutInSec,
                                                      ExpectedCondition<?> condition) {
        boolean flag;

        FluentWait<WebDriver> wait = new WebDriverWait(driver, timeOutInSec).pollingEvery(Duration.ofMillis(200));
        try {
            wait.until(condition);
            flag = true;
        } catch (Exception e) {
            flag = false;
            Log.debug(e.getMessage());
        }

        return flag;
    }

    protected boolean checkIfElementPresent(By by) {

        return getElementsInPage(by).size() > 0;
    }

    protected Boolean waitTillPageLoading(int timeOutInSec) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSec))
                .pollingEvery(Duration.ofMillis(500));
        // .ignoring(NoSuchElementException.class); // either use try catch or use ignoring to ignore exception

        Boolean loaded = wait.until(new Function<WebDriver, Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    return isPageLoadingCompleted();
                } catch (Exception e) {
                    return null;
                }
            }
        });

        return loaded;
    }

}
