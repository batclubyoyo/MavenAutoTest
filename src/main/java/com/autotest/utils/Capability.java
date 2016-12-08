package com.autotest.utils;

import com.autotest.data.XmlUtils;

import org.apache.logging.log4j.Logger;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by batcl on 11/15/2016.
 */
public class Capability {

    private Logger logger = LogRecord.getLogger();

    private String serverURL = "http://" + XmlUtils.getServerURL("server", "ip") + ":"; // Appium and JunitBrowser Both
    private String serverPort = "4444";  // This is for browser hub

    private WebDriver driver;
    private DesiredCapabilities desiredCapability;

    public Capability(String[] args) throws Exception {
        if (args[0].toLowerCase().equals("android") || args[0].toLowerCase().equals("ios")) {
            setMobileDriver(args[0].toLowerCase(), args[1], args[2],args[3].toLowerCase());
        } else if (args[0].toLowerCase().equals("web")) {
//            setLocalDriver(args[1].toLowerCase(), args[2].toLowerCase(), args[3].toLowerCase());
            setRemoteDriver(args[1].toLowerCase(), args[2].toLowerCase(), args[3].toLowerCase());
        }
    }

    public DesiredCapabilities getDesiredCapability() {
        return this.desiredCapability;
    }

    public WebDriver getDriver() { return this.driver; }

    private void setCapability(String platform, String platformVersion, String device) {
        this.desiredCapability = new DesiredCapabilities();
        switch (platform) {
            case "android":
                this.desiredCapability.setCapability(CapabilityType.BROWSER_NAME,"");

                // Device parameter
                this.desiredCapability.setCapability("platformName", platform);
                this.desiredCapability.setCapability("platformVersion", platformVersion);
                this.desiredCapability.setCapability("udid", device);
                this.desiredCapability.setCapability("deviceName", device);

                // App package and launch activity
                this.desiredCapability.setCapability("appPackage", "com.ifangchou.ifangchou");
                this.desiredCapability.setCapability("appActivity", "com.ifangchou.ifangchou.activity.HomeTabActivity");
                break;
            case "ios":
                break;
            default:
                break;
        }
    }

    private void setMobileDriver(String platform, String platformVersion, String device, String port) throws Exception {
        setCapability(platform, platformVersion, device);
        this.driver = new AndroidDriver(new URL(this.serverURL + port + "/wd/hub"), this.desiredCapability);
        this.driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
    }

    // local does not need url so now exception throws
    private void setLocalDriver(String browser, String driver_key, String driver_path) {
        System.setProperty(driver_key, driver_path);
        switch (browser) {
            case "firefox":
                this.driver = new FirefoxDriver();
                break;
            case "chrome":
                this.driver = new ChromeDriver();
                break;
            case "edge":
                this.driver = new EdgeDriver();
                break;
            default:
                this.driver = new InternetExplorerDriver();
                break;
        }
    }

    // System.setProperty(key, path) doesn't work, I still need to set env into operate system in system settings
    private void setRemoteDriver(String browser, String driver_key, String driver_path) throws Exception {
//        String key = driver_key;
//        String path = driver_path;
        System.setProperty(driver_key, driver_path);
        switch (browser) {
            case "firefox":
//                key = "webdriver.firefox.bin";
//                System.setProperty(key, path);
                desiredCapability = DesiredCapabilities.firefox();
                break;
            case "chrome":
//                key = "webdriver.chrome.driver";
//                System.setProperty(key, path);
                desiredCapability = DesiredCapabilities.chrome();
                break;
            case "edge":
//                key = "webdriver.edge.driver";
//                System.setProperty(key, path);
                desiredCapability = DesiredCapabilities.edge();
                break;
            case "safari":
//                key = "webdriver.safari.driver";
//                System.setProperty(key, path);
                desiredCapability = DesiredCapabilities.safari();
                break;
            default:
//                System.setProperty(key, path);
                desiredCapability = DesiredCapabilities.internetExplorer();
                break;
        }

        this.driver = new RemoteWebDriver(new URL(this.serverURL + this.serverPort + "/wd/hub"), desiredCapability);
    }

}
