package com.automation.reporter.customAssert;

import com.automation.reporter.ExtentReporter;
import com.aventstack.extentreports.Status;
import org.testng.Assert;

import static org.testng.internal.EclipseInterface.*;

public class CustomAssert extends Assert {

    public static void assertTrue(boolean condition, String message) {
        if (condition)
            ExtentReporter.getTest().log(Status.PASS, message);
        else
            ExtentReporter.getTest().log(Status.FAIL, message);

        if (!condition) {
            failNotEquals(condition, Boolean.TRUE, message);
        }
    }

    static private void failNotEquals(Object actual, Object expected, String message) {
        fail(format(actual, expected, message));
    }

    static String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
    }

    public static void assertEquals(int actual, int expected) {
        if (actual == expected)
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
        assertEquals(actual, expected, null);
    }

    public static void assertEquals(final String actual, final String expected) {

        if (actual.equals(expected))
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
        assertEquals(actual, expected, null);
    }


    public static void assertEquals(int actual, int expected, String message) {
        if (actual == expected)
            ExtentReporter.getTest().log(Status.PASS, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
        assertEquals(Integer.valueOf(actual), Integer.valueOf(expected), message);
    }

    public static void assertEquals(String actual, String expected, String message) {
        if (actual.equals(expected))
            ExtentReporter.getTest().log(Status.PASS, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));

        assertEquals((Object) actual, (Object) expected, message);
    }
}
