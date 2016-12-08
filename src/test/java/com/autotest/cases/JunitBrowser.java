package com.autotest.cases;

import com.autotest.data.XmlUtils;
import com.autotest.utils.Capability;
import com.autotest.utils.LogRecord;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.Thread.sleep;

/**
 * Created by 10202 on 2016/11/18.
 */

@RunWith(Parameterized.class)
public class JunitBrowser {

    private Logger logger = LogRecord.getLogger();

    private WebDriver driver;
    private WebDriverWait wait;

    private String[] args = new String[4];

    private static Object[][] getParameter() {
        return XmlUtils.getBrowserParameter("pc", "web", "edge");
    }

    @Parameterized.Parameters
    public static Collection data() {
        return  Arrays.asList(getParameter());
    }

    public JunitBrowser(String browser, String key, String path, String platform) {
        this.args[0] = platform;
        this.args[1] = browser;
        this.args[2] = key;
        this.args[3] = path;
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

    @Test
    public void test() throws Exception {
        System.out.println("okokok?????????");
        sleep(5000);
    }

}
