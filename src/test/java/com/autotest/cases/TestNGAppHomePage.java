package com.autotest.cases;

import com.autotest.utils.Capability;
import com.autotest.utils.HomePageLocator;
import com.autotest.utils.LogRecord;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;
import static org.testng.Assert.fail;

/**
 * Created by zhiyun on 2016/11/10.
 */

/*
Directed run this class will run all cases annotation with @Test
Each @Test will invoke setUp()\tearDown() before\after it runs
That means a class can contain numbers of cases
 */

public class TestNGAppHomePage {

    private Logger logger = LogRecord.getLogger();
    private WebDriver driver;
    private WebDriverWait wait;

    private String[] args = new String[4];

    @Parameters({"appiumServerPort","deviceName","platformVersion","platform"})
    public TestNGAppHomePage(String appiumServerPort, String deviceName, String platformVersion, String platform) {
        this.args[0] = platform;
        this.args[1] = platformVersion;
        this.args[2] = deviceName;
        this.args[3] = appiumServerPort;
    }


    @BeforeMethod
    public void setUp() throws Exception {
        logger.info("Using this to initialize driver: " + this.args[0] + ":" + this.args[1] + ":" + this.args[2] + ":" + this.args[3]);

        try {
            Capability capability = new Capability(this.args);
            this.driver = capability.getDriver();

            this.wait = new WebDriverWait(this.driver, 10);
            logger.info("Initialize driver success!\n");
        } catch (Exception e) {
            logger.error("Initialize driver fail!\n" + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        this.driver.quit();
        System.out.println("close???????!!!!!!!!!!!!");
    }

    /*
    invocationCount identify the repeat times of test case, also with the setUp/tearDown
    skipFailedInvocations = true will skip rest invocation count testes when fail/exception happens, but it only works when there is no try

    add try, I can catch screenshot when exception happens, and rest testes will continue, code which is after exception happen will not execute,
    but after finished, it will be marked passed-------------add a fail() in catch() then is ok
    no try, code which is after exception happen will not execute, and rest testes will continue, test marked failed,
    but I can't catch screenshot
     */
    @Test(invocationCount = 2)
    public void openHome() throws Exception {

        try {
            System.out.println("first???????!!!!!!!!!!!!");
            sleep(3000);

            Boolean BL = Boolean.valueOf(false);
            System.out.println("before BL is: " + BL.booleanValue());

            HomePageLocator homePageLocator = new HomePageLocator(this.driver);
            BL = this.wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return Boolean.valueOf(homePageLocator.getLocator_annotation_navigator_list().isDisplayed());
                }
            });

            System.out.println("after BL is: " + BL.booleanValue());

//        WebElement webElement = wait.until(new ExpectedCondition<WebElement>() {
//            @Override
//           public WebElement apply(WebDriver d) {
////               return homePageLocator.getLocator_navigator_home_by();
//                return d.findElement(By.xpath("//android.widget.TabWidget[2]"));
//           }
//        }); // no this element, throw timeout exception; found it, return this element

            homePageLocator.getLocator_annotation_navigator_list().click();
            sleep(3000);


            // If use @BeforeClass/@AfterClass on setUp/tearDown, then will need this
            // If use @BeforeMethod/@AfterMethod on setUp/tearDown, then won't
//            AppControl.reLaunchApp((AppiumDriver) this.driver);

        } catch (Exception e) {
            LogRecord.getScreenshot((AppiumDriver) this.driver);

            logger.error(e.getMessage());
            fail("Exception happen!!!");
        }


    }


}
