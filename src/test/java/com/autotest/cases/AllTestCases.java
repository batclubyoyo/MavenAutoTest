package com.autotest.cases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by zhiyun on 2016/11/14.
 */

/*
Use Junit To run multi cases, each test case uses one device which is identified in test case class file
But it's not parallel, need to find a way

suite can be used like that: 1 device/browser, 1 suite; multi devices/browsers, use suite set

 */

@RunWith(Suite.class)
@Suite.SuiteClasses({JunitAppHomePage.class, JunitAppLoginPage.class})

public class AllTestCases {
}
