package testCases;


import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.*;

public class Initializer {
    public BaseTest baseTest = new BaseTest();
    @BeforeClass
    @Parameters("browser")
    public synchronized void startUp(@Optional String browser) {
        baseTest.startUp(browser);
    }

    @Test
    @Description("Testing the access of the website")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Story Name: To check login functionality")
    public void methodLevelSetup(){
        baseTest.methodLevelSetup();
    }


    @AfterClass
    public void tearDown() {
        baseTest.tearDown();
    }
}
