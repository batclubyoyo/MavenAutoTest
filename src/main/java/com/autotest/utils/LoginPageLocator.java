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
 * Created by 10202 on 2016/11/14.
 */
public class LoginPageLocator {

    private WebDriver driver;

    @FindBy(id = "et_username")
    @AndroidFindBy(id = "et_username")
    @iOSFindBy(id = "et_username")
    private MobileElement locator_username;

    @FindBy(id = "et_password")
    @AndroidFindBy(id = "et_password")
    @iOSFindBy(id = "et_password")
    private MobileElement locator_password;

    @FindBy(id = "bt_login")
    @AndroidFindBy(id = "bt_login")
    @iOSFindBy(id = "bt_login")
    private MobileElement locator_login;

    @FindBy(id = "bt_register")
    @AndroidFindBy(id = "bt_register")
    @iOSFindBy(id = "bt_register")
    private MobileElement locator_register;

    @FindBy(id = "rel_weixin")
    @AndroidFindBy(id = "rel_weixin")
    @iOSFindBy(id = "rel_weixin")
    private MobileElement locator_weixin;

    public LoginPageLocator(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public MobileElement getLocator_annotation_username() {
        return locator_username;
    }

    public MobileElement getLocator_annotation_password() {
        return locator_password;
    }

    public MobileElement getLocator_annotation_login() {
        return locator_login;
    }

    public MobileElement getLocator_annotation_register() {
        return locator_register;
    }

    public MobileElement getLocator_annotation_weixin() {
        return locator_weixin;
    }


    public MobileElement getLocator_by_username() {
        locator_username = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppLoginPage", "username")));
        return locator_username;
    }

    public MobileElement getLocator_by_password() {
        locator_password = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppLoginPage", "password")));
        return locator_password;
    }

    public MobileElement getLocator_by_login() {
        locator_login = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppLoginPage", "login")));
        return locator_login;
    }

    public MobileElement getLocator_by_register() {
        locator_register = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppLoginPage", "register")));
        return locator_register;
    }

    public MobileElement getLocator_by_weixin() {
        locator_weixin = (MobileElement) this.driver.findElement(By.id(XmlUtils.getAppLocator("JunitAppLoginPage", "weixin")));
        return locator_weixin;
    }


}
