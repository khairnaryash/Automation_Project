# Automation_Project

Project uses :
    Selenium - web automation
    RestAssured - API automation
    TestNG - Test suite and configuration control (environment, browser)
    test\resources\data - test data for different test cases
    test\config - configuration for different environment
    drivermanger to get latest webdriver executable
    Extent report - HTML report generation
    Maven - project dependency management, project builder, test execution

In test data file set Execute to "Y"to execute that test case for test data set. This test class must be added to test ng suite.

For Running :
    1) set up test suite in testng suite file
    2) setup test data in test data file
    3) setup application parameters in config file
    4) execute testng suite as Testng test from IDE or using maven test command
    5) in Windows environment double click RunTest.bat to execute test

After execution, you can find reports and log in Output folder.


