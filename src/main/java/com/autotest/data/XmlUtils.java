package com.autotest.data;

import java.util.Map;

/**
 * Created by 10202 on 2016/11/16.
 */
public class XmlUtils {

    private static String xml_file = "src/xml/datasource.xml";

    public static int getRepeat() {
        Xml xml = new Xml();
        xml.parserXml(xml_file, "cycle", "@test", "repeat");
        return Integer.parseInt(xml.getTargetValue());
    }

    // SQL
    public static Map<String, String> getDBMap(String usage, String target, String info) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, usage, target, info);
        return xml.getTargetMap();
    }


    // App + Browser
    public static String getServerURL(String server, String ip) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, "url", server, ip);
        return xml.getTargetValue();
    }


    // App
    public static String getAppLocator(String page, String locator) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, "locator", page.toLowerCase(), locator.toLowerCase());
        return xml.getTargetValue();
    }

    public static Object[][] getAppDeviceParameter(String usage, String target, String device) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, usage.toLowerCase(), target.toLowerCase(), device);
        return xml.getTargetObject("deviceName");
    }

    public static Object[][] getAppDevicesParameter(String usage, String target, String device) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, usage.toLowerCase(), target.toLowerCase(), device);
        return xml.getObjects();
    }


    // Browser
    public static Object[][] getBrowserParameter(String usage, String target, String info) {
        Xml xml = new Xml();
        xml.parserXml(xml_file, usage, target, info);
        return xml.getTargetObject("browser");
    }

}
