package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelReader;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Sally Jarkas
 *
 */
//@Listeners({TestAllureListener.class})
public class BaseTest {

    public static WebDriver driver = null;
    protected static String path = System.getProperty("user.dir") + "/src/test/resources/";
    protected static Properties config = new Properties();
    protected static Properties element = new Properties();
    protected static WebDriverWait wait;

    protected static FileInputStream fis;
    protected static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/data-files/dataFile.xlsx");

    protected static JavascriptExecutor executor;
    protected static Robot robot;
    protected static Actions action;


    public synchronized void startUp(String browser) {
        if (driver == null) {
            try {
                fis = new FileInputStream(path + "properties/config.properties");
                config.load(fis);
            } catch (Exception e) {
                e.fillInStackTrace();
                System.out.println(path + " Unable to Load Resources");
            }
        }
        if (browser.equals("firefox")) {
            try {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                System.out.println(driver);
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
                System.out.println("Can't load the driver");
            }

        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--disable-gpu");

            try {
                driver = new ChromeDriver(options);
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
    }



    public void methodLevelSetup(){
        System.out.println("Starting ......");
        try {
            driver.get(config.getProperty("baseURL"));
            executor = (JavascriptExecutor) BaseTest.driver;
            action = new Actions(driver);
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.fillInStackTrace());
        }
    }


    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // helper functions
    protected void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.printf("Timeout waiting for Page Load Request to complete.");
        }
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (Exception e) {
            System.out.println("Capture Failed " + e.getMessage());
        }
        return path;
    }
}
