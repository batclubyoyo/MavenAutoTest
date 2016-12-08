package com.autotest.utils;

import com.autotest.data.XmlUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by zhiyun on 2016/11/14.
 */
public class HomePageLocator {

    private WebDriver driver;

    @FindBy(xpath = "//android.widget.TabWidget[0]")
    @AndroidFindBy(xpath = "//android.widget.TabWidget[0]")
    @iOSFindBy(xpath = "//android.widget.TabWidget[0]")
    private MobileElement locator_navigator_home;

    @FindBy(xpath = "//android.widget.TabWidget[1]")
    @AndroidFindBy(xpath = "//android.widget.TabWidget[1]")
    @iOSFindBy(xpath = "//android.widget.TabWidget[1]")
    private MobileElement locator_navigator_list;

    @FindBy(xpath = "//android.widget.TabWidget[2]")
    @AndroidFindBy(xpath = "//android.widget.TabWidget[2]")
    @iOSFindBy(xpath = "//android.widget.TabWidget[2]")
    private MobileElement locator_navigator_mine;

    @FindBy(id = "me_homepage")
    @AndroidFindBy(id = "me_homepage")
    @iOSFindBy(id = "me_homepage")
    private MobileElement locator_message;

    @FindBy(id = "login_text_view")
    @AndroidFindBy(id = "login_text_view")
    @iOSFindBy(id = "login_text_view")
    private MobileElement locator_before_login; // before login

    @FindBy(id = "phone_num_view")
    @AndroidFindBy(id = "phone_num_view")
    @iOSFindBy(id = "phone_num_view")
    private MobileElement locator_after_login;  // after login


    public HomePageLocator(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public MobileElement getLocator_annotation_navigator_home() {
        System.out.println("invoke this?????????????");
        return locator_navigator_home;
    }

    public MobileElement getLocator_annotation_navigator_list() {
        System.out.println("invoke this?????????????");
        return locator_navigator_list;
    }

    public MobileElement getLocator_annotation_navigator_mine() {
        System.out.println("invoke this?????????????");
        return locator_navigator_mine;
    }

    public MobileElement getLocator_annotation_message() {
        System.out.println("invoke this?????????????");
        return locator_message;
    }

    public MobileElement getLocator_annotation_before_login() {
        System.out.println("invoke this?????????????");
        return locator_before_login;
    }

    public MobileElement getLocator_annotation_after_login() {
        System.out.println("invoke this?????????????");
        return locator_after_login;
    }


    public MobileElement getLocator_by_navigator_home() {
        locator_navigator_home = (MobileElement) this.driver.findElement(By.xpath(XmlUtils.getAppLocator("JunitAppHomePage", "navigator_home")));
        return locator_navigator_home;
    }

    public MobileElement getLocator_by_navigator_list() {
        locator_navigator_list = (MobileElement) this.driver.findElement(By.xpath(XmlUtils.getAppLocator("JunitAppHomePage", "navigator_list")));
        return locator_navigator_list;
    }

    public MobileElement getLocator_by_navigator_mine() {
        locator_navigator_mine = (MobileElement) this.driver.findElement(By.xpath(XmlUtils.getAppLocator("JunitAppHomePage", "navigator_mine")));
        return locator_navigator_mine;
    }

    public MobileElement getLocator_by_message() {
        locator_message = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppHomePage", "message")));
        return locator_message;
    }

    public MobileElement getLocator_by_before_login() {
        locator_before_login = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppHomePage", "before_login")));
        return locator_before_login;
    }

    public MobileElement getLocator_by_after_login() {
        locator_after_login = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppHomePage", "after_login")));
        return locator_after_login;
    }


}
