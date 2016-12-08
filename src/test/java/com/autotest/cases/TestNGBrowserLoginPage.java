package com.autotest.cases;

import com.autotest.utils.Capability;
import com.autotest.utils.LogRecord;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static java.lang.Thread.sleep;

/**
 * Created by 10202 on 2016/11/21.
 */
public class TestNGBrowserLoginPage {

    private Logger logger = LogRecord.getLogger();
    private WebDriver driver;
    private WebDriverWait wait;

    private String[] args = new String[4];

    @Parameters({"platform","browser","key","path"})
    public TestNGBrowserLoginPage(String platform, String browser, String key, String path) {
        this.args[0] = platform;
        this.args[1] = browser;
        this.args[2] = key;
        this.args[3] = path;
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

    @org.testng.annotations.Test
    public void test() throws Exception {
        System.out.println("are you okokok?????????");
        sleep(5000);
    }

}
