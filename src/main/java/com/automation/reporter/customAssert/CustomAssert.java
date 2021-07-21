package com.automation.reporter.customAssert;

import com.automation.reporter.ExtentReporter;
import com.automation.reporter.Log;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.internal.EclipseInterface;

import static org.testng.internal.EclipseInterface.*;

public class CustomAssert extends Assert {

    public static void assertTrue(boolean condition, String message) {
        if (condition) {
            ExtentReporter.getTest().log(Status.PASS, message);
            Log.info("Validation passed : " + message);
        } else {
            ExtentReporter.getTest().log(Status.FAIL, message);
            Log.error("Validation failed : " + message);
        }
        if (!condition) {
            failNotEquals(condition, Boolean.TRUE, message);
        }
    }

    private static void assertEqualsImpl(Object actual, Object expected, String message) {
        boolean equal = areEqualImpl(actual, expected);
        if (!equal) {
            failNotEquals(actual, expected, message);
        }

    }

    private static boolean areEqualImpl(Object actual, Object expected) {
        if (expected == null && actual == null) {
            return true;
        } else if (expected == null ^ actual == null) {
            return false;
        } else {
            return expected.equals(actual) && actual.equals(expected);
        }
    }

    private static void failNotEquals(Object actual, Object expected, String message) {
        fail(format(actual, expected, message, true));
    }
    static String format(Object actual, Object expected, String message, boolean isAssertEquals) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return isAssertEquals ? formatted + EclipseInterface.ASSERT_EQUAL_LEFT + expected + EclipseInterface.ASSERT_MIDDLE + actual + EclipseInterface.ASSERT_RIGHT : formatted + EclipseInterface.ASSERT_UNEQUAL_LEFT + expected + EclipseInterface.ASSERT_MIDDLE + actual + EclipseInterface.ASSERT_RIGHT;
    }

    public static void assertEquals(int actual, int expected) {
        if (actual == expected) {
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
            Log.info(String.format("Expected :%s, actual :%s", expected, actual));
        } else {
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
            Log.error(String.format("Expected :%s, actual :%s", expected, actual));
        }
        assertEquals(actual, expected, null);
    }

    public static void assertEquals(final String actual, final String expected) {

        if (actual.equals(expected)) {
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
            Log.info(String.format("Expected :%s, actual :%s", expected, actual));
        } else {
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
            Log.error(String.format("Expected :%s, actual :%s", expected, actual));
        }
        assertEquals(actual, expected, null);
    }


    public static void assertEquals(int actual, int expected, String message) {
        if (actual == expected) {
            ExtentReporter.getTest().log(Status.PASS, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
            Log.info(String.format("%s ,Expected :%s, actual :%s", message, expected, actual));
        } else {
            ExtentReporter.getTest().log(Status.FAIL, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
            Log.error(String.format("%s ,Expected :%s, actual :%s", message, expected, actual));
        }
        assertEquals(Integer.valueOf(actual), Integer.valueOf(expected), message);
    }

    public static void assertEquals(String actual, String expected, String message) {
        if (actual.equals(expected)) {
            ExtentReporter.getTest().log(Status.PASS, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
            Log.info(String.format("%s ,Expected :%s, actual :%s", message, expected, actual));
        } else {
            ExtentReporter.getTest().log(Status.FAIL, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
            Log.error(String.format("%s ,Expected :%s, actual :%s", message, expected, actual));

        }
        assertEquals((Object) actual, (Object) expected, message);
    }
}
