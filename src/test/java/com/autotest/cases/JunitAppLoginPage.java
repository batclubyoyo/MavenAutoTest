package com.autotest.cases;

import com.autotest.data.XmlUtils;
import com.autotest.utils.AppControl;
import com.autotest.utils.Capability;
import com.autotest.utils.LogRecord;

import io.appium.java_client.AppiumDriver;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.Thread.sleep;
import static org.testng.Assert.fail;

/**
 * Created by 10202 on 2016/11/15.
 */

@RunWith(Parameterized.class)
public class JunitAppLoginPage {

    private Logger logger = LogRecord.getLogger();

    private WebDriver driver;
    private WebDriverWait wait;

    private int repeat = XmlUtils.getRepeat();
    private String[] args = new String[4];

    private static Object[][] getParameter() {
        return XmlUtils.getAppDeviceParameter("appium", "Android", "DU2TAN1576061867");
    }

    private static Object[][] getParameters() {
        return XmlUtils.getAppDevicesParameter("appium", "Android", "");
    }

    @Parameterized.Parameters
    public static Collection data() {
//        return Arrays.asList(getParameters());   // If need to test multi devices, use this
        return  Arrays.asList(getParameter());
    }

    public JunitAppLoginPage(String appiumServerPort, String deviceName, String platformVersion, String platform) {
        this.args[0] = platform;
        this.args[1] = platformVersion;
        this.args[2] = deviceName;
        this.args[3] = appiumServerPort;
    }

    @Before
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

    @After
    public void tearDown() throws Exception {
        this.driver.quit();
        System.out.println("close???????!!!!!!!!!!!!");
    }



    @Test
    public void openLogin() throws Exception {
        int repeat = this.repeat;
        boolean result = true;
        while (repeat > 0) {
            repeat--;
            try {
                System.out.println("login???????!!!!!!!!!!!!");
                sleep(3000);

                AppControl.reLaunchApp((AppiumDriver) this.driver);
            } catch (Exception e) {
                LogRecord.getScreenshot((AppiumDriver) this.driver);

                logger.error(e.getMessage());
                result = false;
            } finally {
                continue;
            }
        }

        if (!result) {
            fail("Exception happen!!!"); //add it will make rest testes break
        }
    }


}
