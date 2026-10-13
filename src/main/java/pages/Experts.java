package pages;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class Experts extends BaseTest {

    public Experts(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
}
