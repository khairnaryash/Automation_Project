package com.automation.utils;

import com.automation.Exceptions.FileNotFoundException;
import com.automation.Exceptions.InvalidValueException;
import com.automation.reporter.ExtentReporter;
import com.automation.reporter.Log;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class BaseTest implements ITest {

    public static String reportFolderPath = null;
    protected Properties properties;
    protected String environment;
    protected String testName;

    @BeforeSuite
    public void initialise(ITestContext context) {

        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

        String d = ft.format(new Date()).replace("-", "_");

        // System.getProperty("user.dir")

        reportFolderPath = System.getProperty("user.dir") + "/Output/Report_" + d;

        new File(reportFolderPath + "/Screenshots").mkdirs();

        Log.setlogger(reportFolderPath);
        Log.info("************* Log set up successfull **************");

        Log.debug("Report Folder : " + reportFolderPath);

        Map<String, String> paraMap = context.getCurrentXmlTest().getAllParameters();

        ExtentReporter.setupExtentReport(reportFolderPath,paraMap);

    }



    @BeforeMethod
    public void setTestName(Object[] obj) {
        HashMap<String, String> testData =   (HashMap<String, String>) obj[0];
        if (testData.containsKey("TestCaseName") && !testData.get("TestCaseName").isEmpty())
            testName = testData.get("TestCaseName");

    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Parameters({"environment"})
    @BeforeClass
    public void testSetup1(String environment_) {
        environment = environment_;
        Log.info("environment: " + environment);
        loadProperties(String.format("config/%s/application.properties", environment));

    }

   /* @BeforeMethod
	public void beforeMethod( Method method){

		Log.info("********  Starting test case :" + method.getName());

		ExtentReporter.createTest(method.getName());

	}*/



	
	/*@AfterSuite
	public void tearDownAfterSuite(ITestContext context)  {
		Log.info("********* Clossing Reporter *********");
		ExtentReporter.closeExtentReport();

	}*/

    public void loadProperties(String fileName) {

        fileName = fileName.endsWith(".properties") ? fileName : fileName + ".properties";
        InputStream io = ClassLoader.getSystemResourceAsStream(fileName);

        if (io == null)
            throw new FileNotFoundException("Properties file not found : " + fileName);

        Properties pro = new Properties();
        try {
            pro.load(io);
        } catch (Exception e) {
            throw new InvalidValueException("Properties file not properly formatted : " + fileName);
        }
        if (properties == null)
            properties = pro;
        properties.putAll(pro);
    }

    protected String getProperty(String key) {
        if (properties.containsKey(key))
            return properties.getProperty(key);

        throw new InvalidValueException(String.format("Property value %s not found in properties", key));
    }

    public Object[][] getDataFromTestDataFile(String fileName, String sheet) {

        fileName = String.format("data/%s/%s", environment, fileName);

        Log.info("Reading test data from file : " + fileName);
        // add execution flag check
        Object[][] data = ExcelUtils.readExcelData(fileName, sheet);
        Object[][] testData = new Object[0][0];
        int i = 1;
        for (Object[] obj : data) {

            HashMap<String, String> map = (HashMap) obj[0];
            if (map.get("Execute") != null && map.get("Execute").equalsIgnoreCase("y")) {
                map.remove("Execute");
                Object[] o = new Object[1];
                o[0] = map;
                Object[][] testDataTemp = testData;
                testData = new Object[i][1];
                for (int j = 0; j < testDataTemp.length; j++)
                    testData[j] = testDataTemp[j];

                testData[i - 1] = o;
                i++;
            }
        }
        Log.info("Total data sets loaded : " + testData.length);
        return testData;
    }


}
