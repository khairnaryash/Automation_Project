package com.automation.utils;

import com.automation.enums.SupportedBrowser;
import com.automation.reporter.Log;
import com.automation.reporter.SeleniumEventListner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class SeleniumDriverManager {


    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        return webDriverThreadLocal.get();
    }

    private static void setWebDriver(WebDriver driver) {
        EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
        SeleniumEventListner handler = new SeleniumEventListner();
        eventDriver.register(handler);
        webDriverThreadLocal.set(eventDriver);
    }


    public static void initializeDriver(SupportedBrowser browser, String version) {

        Log.debug("Initializing Browser : " + browser.name());

        System.setProperty("wdm.targetPath", "target/webdrivers");

        Log.debug("instance : " + Thread.currentThread().getName());
        switch (browser) {

            case CHROME: {
                WebDriverManager.chromedriver().arch32().version(version).setup();
//                WebDriverManager.chromedriver().setup();

                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//		chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
                chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);
                options.addArguments("--start-maximized");
                WebDriver d = new ChromeDriver(options);
                setWebDriver(d);
                break;
            }
            case CHROME_HEADLESS: {
                WebDriverManager.chromedriver().arch32().version(version).setup();
//                WebDriverManager.chromedriver().setup();

                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//		chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
                chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);
//                options.addArguments("--start-maximized");
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");

                Log.info("Thread id : " + Thread.currentThread().getId());
                WebDriver d = new ChromeDriver(options);
                Log.info(d.toString());
                setWebDriver(d);
                break;
            }
            case FIREFOX: {

                WebDriverManager.firefoxdriver().version(version).setup();

                // initialize FireFox driver
                setWebDriver(new FirefoxDriver());

                break;
            }

        }


    }

    public static void moveWithPruneEmptydirectories(String source, String target) {
        Path sourcePath;
        Path targetPath;

        try {
            sourcePath = FileSystems.getDefault().getPath(source);
            targetPath = FileSystems.getDefault().getPath(target);

            createDirectoryIfNotExists(targetPath);

            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            Files.deleteIfExists(FileSystems.getDefault().getPath("./target/webdrivers"));

        } catch (IOException | NullPointerException e) {
            System.out.println("Unable to move file from: [" + source + "] to: [" + target + "]");
        }
    }

    private static void createDirectoryIfNotExists(Path targetDirPath) {
        if (!Files.exists(targetDirPath)) {
            try {
                Files.createDirectories(targetDirPath);
            } catch (IOException e) {
                System.out.println("Unable to create directories: [" + targetDirPath + "].");
            }
        }
    }

}
