package com.autotest.utils;

import org.openqa.selenium.OutputType;
import io.appium.java_client.AppiumDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by batcl on 10/14/2016.
 */

public class LogRecord {

    private static Logger logger = LogManager.getLogger();

    private static final String project_dir = System.getProperty("user.dir");

    // This only uses in test cases
    public static void getScreenshot(AppiumDriver driver) {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String file_name = String.format(project_dir + "//log//" + dateFormat.format(date) + ".png");
            File src_file = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src_file, new File(file_name));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Save Screenshot Fail" + "\n" +e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }

}
