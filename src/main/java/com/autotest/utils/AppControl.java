package com.autotest.utils;

import io.appium.java_client.AppiumDriver;

import org.apache.logging.log4j.Logger;

import static java.lang.Thread.sleep;

/**
 * Created by 10202 on 2016/11/17.
 */
public class AppControl {

    private static Logger logger = LogRecord.getLogger();

    public static void reLaunchApp(AppiumDriver driver) {
        try {
            driver.closeApp();
            sleep(1000);

            driver.launchApp();
            sleep(3000);
        } catch (InterruptedException e) {
            logger.error("Re-launch App Fail!\n" + e.getMessage());
        }

    }

}
