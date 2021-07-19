package com.automation.utils;

import java.io.File;
import java.io.IOException;

import com.automation.reporter.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class CommonHelper {

    /**
     * This method waits for number of seconds provided.
     *
     * @param noOfSeconds
     */
    public static void wait(int noOfSeconds) {

        try {
            Thread.sleep(noOfSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mouseHover(WebElement ele, WebDriver driver) {

        Actions act = new Actions(driver);

        act.moveToElement(ele);

        act.perform();

    }

    public static void doubleClick(WebElement ele, WebDriver driver) {

        Actions act = new Actions(driver);

        act.doubleClick(ele);

        act.perform();

    }

    public static void rightClick(WebElement ele, WebDriver driver) {

        Actions act = new Actions(driver);

        act.contextClick(ele);

        act.perform();

    }

    public static void dragAndDrop(WebElement sourceElement, WebElement targetElement, WebDriver driver) {

        Actions act = new Actions(driver);

        act.dragAndDrop(sourceElement, targetElement);

        act.perform();

    }

    public static void dragAndDropByOffset(WebElement sourceElement, int xOffset, int yOffset, WebDriver driver) {

        Actions act = new Actions(driver);

        act.dragAndDropBy(sourceElement, xOffset, yOffset);

        act.perform();

    }

    public static void pressEnterKey(WebDriver driver) {

        Actions act = new Actions(driver);

        act.keyDown(Keys.ENTER);

        act.perform();

        act.keyUp(Keys.ENTER);

        act.perform();

    }


    /**
     * Changes Element CSS to make element visible
     *
     * @param element
     * @param driver
     */
    public static void makeElementVisible(WebElement element, WebDriver driver) {

        JavascriptExecutor Executor = (JavascriptExecutor) driver;

        // change visibility of element
        Executor.executeScript("arguments[0].setAttribute('style', 'display : block;');", element);

    }

    /**
     * Changes Element CSS to make element visible
     *
     * @param element
     * @param driver
     */
    public static void hideElement(WebElement element, WebDriver driver) {

        JavascriptExecutor Executor = (JavascriptExecutor) driver;

        // change visibility of element
        Executor.executeScript("arguments[0].setAttribute('style', 'display : none;');", element);

    }

    /**
     * Scrolls web page to element using Java Script
     *
     * @param element
     * @param driver
     */
    public static void scrollToElement(WebElement element, WebDriver driver) {

        JavascriptExecutor Executor = (JavascriptExecutor) driver;

        Executor.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public static boolean isAlertPresent(WebDriver driver) {
        boolean present = false;

        try {
            driver.switchTo().alert();
            present = true;
        } catch (NoAlertPresentException e) {
            present = false;
        }

        return present;
    }

    public static String getAlertText(WebDriver driver) {

        String text = null;
        try {
            text = driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {

            System.out.println("Alert not present");

        }

        return text;
    }

    public static void dismissAlert(WebDriver driver) {

        try {
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {

            System.out.println("Alert not present");

        }
    }

    public static void acceptAlert(WebDriver driver) {

        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {

            System.out.println("Alert not present");

        }
    }


    /**
     * Takes screen shot of screen.
     *
     * @param driver
     * @param methodName
     */
    public static void takeScreenShot(WebDriver driver, String methodName) {

        String fileNameWithPath = null;
        File scrFile = null;

        try {

            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        } catch (WebDriverException e) {
            Log.error("Unble to take screenshot ");
            e.printStackTrace();
            return;
        }

        fileNameWithPath = BaseTest.reportFolderPath + File.separator + "Screenshots" + File.separator + methodName + ".jpeg";

        File scrShot = new File(fileNameWithPath);

        if (null != scrShot && scrShot.exists()) {

            String retryFileName = BaseTest.reportFolderPath + File.separator + "Screenshots" + File.separator + methodName + "_retry.jpeg";

            File failedScrShot = new File(retryFileName);

            if (failedScrShot.exists()) {
                failedScrShot.delete();
            }

            try {
                FileUtils.moveFile(scrShot, failedScrShot);
            } catch (IOException e) {
                Log.error("Unble to take screenshot ");
                e.printStackTrace();
            }

        }
        try {
            FileUtils.moveFile(scrFile, scrShot);
        } catch (IOException e) {
            Log.error("Unble to take screenshot ");
            e.printStackTrace();
        }

    }


    public static String takeScreenShot2(WebDriver driver, String mName) {

        String path = null;

        path = "Screenshots" + File.separator + mName + ".jpeg";

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File destFile = new File(BaseTest.reportFolderPath + File.separator + path);
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (Exception e) {
        }
        return path;
    }
}
