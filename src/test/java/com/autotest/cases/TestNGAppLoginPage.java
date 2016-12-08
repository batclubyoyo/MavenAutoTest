package com.autotest.cases;

import com.autotest.utils.Capability;
import com.autotest.utils.LogRecord;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.apache.logging.log4j.Logger;

import static java.lang.Thread.sleep;
import static org.testng.Assert.fail;

/**
 * Created by 10202 on 2016/11/15.
 */

public class TestNGAppLoginPage {


    private Logger logger = LogRecord.getLogger();
    private WebDriver driver;
    private WebDriverWait wait;

    private String[] args = new String[4];

    @Parameters({"appiumServerPort","deviceName","platformVersion","platform"})
    public TestNGAppLoginPage(String appiumServerPort, String deviceName, String platformVersion, String platform) {
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


    @Test(invocationCount = 2, skipFailedInvocations = true)
    public void openLogin() throws Exception {

        try {
            System.out.println("login???????!!!!!!!!!!!!");
            sleep(3000);

        } catch (Exception e) {
            LogRecord.getScreenshot((AppiumDriver) this.driver);

            logger.error(e.getMessage());
            fail("Exception happen!!!");
        }



    }


}
