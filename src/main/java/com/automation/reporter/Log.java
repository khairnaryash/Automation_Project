package com.automation.reporter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.markuputils.ExtentColor;
//import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Log {

	static Logger logger;

	public static void setlogger(String folderPath) {

		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%d{HH:mm:ss}  %-5.5p [%t]  %m%n");
		// "%-7p %d [%t] %c %x - %m%n"
		// %d{HH:mm:ss} %-5.5p %m%n

		FileAppender fileA = new FileAppender();
		fileA.setFile(folderPath + "\\Log.log");
		fileA.setLayout(layout);
		fileA.activateOptions();

		ConsoleAppender consoleA = new ConsoleAppender();
		consoleA.setLayout(layout);
		consoleA.activateOptions();

		logger = Logger.getLogger("Detailed_Logs");

		logger.setLevel(Level.DEBUG);
		logger.addAppender(fileA);
		logger.addAppender(consoleA);

	}

	/**
	 * This method gets the class name at the runtime for all the log level
	 * methods of the Log Class from the StackTrace.
	 * 
	 * @return ClassName
	 */
	private static String getClassName() {

		return new Throwable().getStackTrace()[3].getClassName();
	}

	/**
	 * This method gets the Method name at the runtime for all the log level
	 * methods of the Log Class from the StackTrace.
	 * 
	 * @return MethodName
	 */
	private static String getMethodName() {

		return new Throwable().getStackTrace()[3].getMethodName();
	}

	/**
	 * This method gets the name of the method which is calling the other method
	 * at the runtime.
	 * 
	 * @return ClassName
	 */
	private static int getLineNumber() {

		return new Throwable().fillInStackTrace().getStackTrace()[2].getLineNumber();

	}

	/**
	 * Method returns the information the class:method & and line number from
	 * where the logging method ( info(),debug().. ) is called. This is not the
	 * best way.. but had to get it to work
	 * 
	 * @return Returns call information in <ClassName>:<MethodName> <Line
	 *         Number> format
	 */
	private static String getCallInfo() {

		String callInfo;
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

		callInfo = getClassName() + ":" + methodName + "-[" + lineNumber + "]-";
		return callInfo;

	}

	private static Logger getLog() {

		return logger;

	}

	/**
	 * This Method designates fine-grained informational events that are most
	 * useful to debug an application.
	 * 
	 * @param message
	 */
	public static void debug(Object message) {

		getLog().debug(getCallInfo() + message);
	}

	/**
	 * This Method designates error events that might still allow the
	 * application to continue running.
	 * 
	 * @param message
	 */
	public static void error(Object message) {

		getLog().error(getCallInfo() + message);
	}

	/**
	 * This Method designates very severe error events that will presumably lead
	 * the application to abort.
	 * 
	 * @param message
	 */
	public static void fatal(Object message) {
		getLog().fatal(getCallInfo() + message);
	}

	/**
	 * This Method designates informational messages that highlight the progress
	 * of the application at coarse-grained level.
	 * 
	 * @param message
	 */
	public static void info(Object message) {

		getLog().info(getCallInfo() + message);
	}

	/**
	 * Use this method to enter success log in Extent repor
	 * 
	 * @param message
	 *//*
	public static void extentReportSuccessLog(String message) {
		ExtentReporter.getExtentTest().log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
	}*/

	/**
	 * Use this method to enter log in Extent report
	 * 
	 * @param message
	 *//*
	public static void ExtentReportFailureLog(String message) {
		ExtentReporter.getExtentTest().log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
	}
	
	*//**
	 * Use this method to enter success log in Extent repor
	 * 
	 * @param message
	 *//*
	public static void ExtentReportLog(String message) {
		ExtentReporter.getExtentTest().log(Status.INFO, MarkupHelper.createLabel(message, ExtentColor.BLUE));
	}*/

	/**
	 * This Method designates potentially harmful situations.
	 * 
	 * @param message
	 */
	public static void warn(Object message) {
		getLog().warn(getCallInfo() + message);
	}
}