package com.automation.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;

public class ExtentReporter implements ITestListener {

    private static ExtentHtmlReporter extentHTML;
    private static ExtentReports extent;
    static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    public static ExtentTest getTest(){
      return  extentTestThreadLocal.get();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        extentTestThreadLocal.set(extent.createTest(iTestResult.getTestName()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        extentTestThreadLocal.set(extent.createTest(iTestResult.getTestName()).fail(iTestResult.getThrowable().getMessage()));

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        extentTestThreadLocal.set(extent.createTest(iTestResult.getTestName()).skip(iTestResult.getThrowable().getMessage()));

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        extentTestThreadLocal.set(extent.createTest(iTestResult.getTestName()).fail(iTestResult.getThrowable().getMessage()));
    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extent.flush();
    }

    public static void setupExtentReport(String reportFolderPath, Map<String, String> paraMap) {

        extentHTML = new ExtentHtmlReporter(reportFolderPath + "/ExecutionReport.html");
        extent = new ExtentReports();

        extent.attachReporter(extentHTML);


        extent.setSystemInfo("OS", "Windows 7");
        extent.setSystemInfo("Host Name", "Yashodeep-PC");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Yashodeep");

        extentHTML.config().setChartVisibilityOnOpen(true);
        extentHTML.config().setDocumentTitle("Demo Report");
        extentHTML.config().setReportName("My Report");
        extentHTML.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHTML.config().setTheme(Theme.STANDARD);

        for (String para : paraMap.keySet()) {

            if (!para.equalsIgnoreCase("password")) {
                extent.setSystemInfo(para, paraMap.get(para));
            }
        }

    }
}
