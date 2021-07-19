package com.automation.reporter.customAssert;

import com.automation.reporter.ExtentReporter;
import com.aventstack.extentreports.Status;
import org.testng.asserts.Assertion;

public class CustomAssertion extends Assertion {

    public void assertEquals(final String actual, final String expected) {

        if (actual.equals(expected))
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
        super.assertEquals(actual, expected);
    }
}
