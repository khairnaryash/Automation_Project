package com.automation.reporter.customAssert;

import com.automation.reporter.ExtentReporter;
import com.aventstack.extentreports.Status;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CustomAssertion extends Assertion {

    public void assertEquals(final String actual, final String expected) {

        if (actual.equals(expected))
            ExtentReporter.getTest().log(Status.PASS, String.format("Expected :%s, actual :%s", expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("Expected :%s, actual :%s", expected, actual));
        super.assertEquals(actual, expected);
    }

    public void assertEquals(final String actual, final String expected, final String message) {
        if (actual.equals(expected))
            ExtentReporter.getTest().log(Status.PASS, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));
        else
            ExtentReporter.getTest().log(Status.FAIL, String.format("<b>%s </b><br> Expected :%s, actual :%s", message, expected, actual));

        doAssert(new SimpleAssert<String>(actual, expected, message) {
            @Override
            public void doAssert() {
                org.testng.Assert.assertEquals(actual, expected, message);
            }
        });
    }


    abstract private static class SimpleAssert<T> implements IAssert<T> {
        private final T actual;
        private final T expected;
        private final String m_message;

        public SimpleAssert(String message) {
            this(null, null, message);
        }

        public SimpleAssert(T actual, T expected) {
            this(actual, expected, null);
        }

        public SimpleAssert(T actual, T expected, String message) {
            this.actual = actual;
            this.expected = expected;
            m_message = message;
        }

        @Override
        public String getMessage() {
            return m_message;
        }

        @Override
        public T getActual() {
            return actual;
        }

        @Override
        public T getExpected() {
            return expected;
        }

        @Override
        abstract public void doAssert();
    }


}
