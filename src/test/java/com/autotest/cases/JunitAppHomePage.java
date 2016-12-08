package com.autotest.cases;

import com.autotest.utils.Capability;
import com.autotest.data.XmlUtils;
import com.autotest.utils.AppControl;
import com.autotest.utils.HomePageLocator;
import com.autotest.utils.LogRecord;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

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

@RunWith(Parameterized.class)
public class JunitAppHomePage {

    private Logger logger = LogRecord.getLogger();

    private WebDriver driver;
    private WebDriverWait wait;

    private int repeat = XmlUtils.getRepeat();
    private String[] args = new String[4];

    // This is only for one instance using the device name to identify
    private static Object[][] getParameter() {
        return XmlUtils.getAppDeviceParameter("appium", "Android", "250285de");
    }

    private static Object[][] getParameters() {
        return XmlUtils.getAppDevicesParameter("appium", "Android", "");
    }

    @Parameterized.Parameters
    public static Collection data() {
//        return Arrays.asList(new String[][] {{"4723", "250285de", "5.0.1", "Android"}}); // example
//        return Arrays.asList(getParameters());   // If need to test multi devices, use this
        return  Arrays.asList(getParameter());

    }

    public JunitAppHomePage(String appiumServerPort, String deviceName, String platformVersion, String platform) {
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
        System.out.println("quit???????!!!!!!!!!!!!");
    }


    /**
     * Request of @Test:
     * 1. Test case can do repeating;
     * 2. Exception happens, this time breaks, but next round still continue;
     * 3. Any Exception happens, treat the Test case as fail
     *
     * Solve:
     * Not found a good way in Junit4(It seems that using Spring's @Repeat(n) can solve my request)
     * Use my own way: using cycle, and a test result identification
     *
     * @throws Exception
     */
    @Test
    public void openHome() throws Exception {
        int repeat = this.repeat;
        boolean result = true;
        while (repeat > 0) {
            repeat--;
            try {
                System.out.println("first???????!!!!!!!!!!!!");
                sleep(3000);

                Boolean BL = Boolean.valueOf(false);
                System.out.println("before BL is: " + BL.booleanValue());

                HomePageLocator homePageLocator = new HomePageLocator(this.driver);
//        Boolean BL = wait.until( (x) -> homePageLocator.getLocator_navigator_home() == null ? Boolean.valueOf(false) : Boolean.valueOf(true));
                BL = this.wait.until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        return Boolean.valueOf(homePageLocator.getLocator_annotation_navigator_list().isDisplayed());
                    }
                });

                System.out.println("after BL is: " + BL.booleanValue());
                homePageLocator.getLocator_annotation_navigator_list().click();
                sleep(3000);

                AppControl.reLaunchApp((AppiumDriver) this.driver);

            } catch (Exception e) {
                LogRecord.getScreenshot((AppiumDriver) this.driver);

                logger.error(e.getMessage());
//                fail("Exception happen!!!"); //add it will make rest testes break
                result = false;
            } finally {
                continue;
            }
        }

        if (!result) {
            fail("Exception happen!!!"); //add it will make rest testes break
        }

    }




//    @Test
//    public void Repeat() throws Exception {
//        List<Result> results = new ArrayList<>();
//        int repeat = 2;
//        while (repeat > 0) {
//            repeat--;
//            Result result = JUnitCore.runClasses(this.getClass());
//            results.add(result);
//        }
//
//
//    }

}
