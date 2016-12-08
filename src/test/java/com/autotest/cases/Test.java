package com.autotest.cases;

import com.autotest.data.XmlUtils;
import com.autotest.utils.LogRecord;

import org.apache.logging.log4j.Logger;

/**
 * Created by batcl on 10/14/2016.
 */
public class Test {

    /**
     * Appium Multi Device Start Up Parameter
     * appium -a 127.0.0.1 -p 4725 -bp 4126 -U "DU2TAN1576061867" --device-name "DU2TAN1576061867" --platform-name Android --platform-version 19 --automation-name Appium --log-no-color
     * appium -a 127.0.0.1 -p 4723 -bp 4125 -U "250285de" --device-name "250285de" --platform-name Android --platform-version 21 --automation-name Appium --log-no-color
     */

    /**
     * Selenium hub/node start parameter
     * ENV: need to set env of drivers that located
     * hub: java -jar .\selenium-server-standalone-2.53.1.jar -role hub
     * node: java -jar .\selenium-server-standalone-2.53.1.jar -role node -hub http://127.0.0.1:4444/grid/register -browser browserName="MicrosoftEdge",webdriver.edge.driver="C:\\AutoTestLibraries\\browser_drivers\\MicrosoftWebdriver.exe",platform=WINDOWS
     * Usage: use linux as hub, and windows as node
     */


    /*
    1. testng's listener/report----OK
    2. driver's final page after one round test-----OK, but need optimize-----junit needs, testng doesn't
    3. check if annotation can use xml source data----not found a way
    4. assert needed to be added
    5. junit annotation of repeat testcase------Not found ways-----use a while instead


    6. desired capability need to be satisfy all platform, web\android\ios..-----now can start up, but need to optimize
    7. until still has problem-----OK
    8. remote server of browser

    9. Modify Capability so all kinds of driver use DesiredCapabilities way to initialize---------OK
    10.Modify Xml format to reduce changing code


     */

    public static Logger logger = LogRecord.getLogger();

    public static void main(String args[]) throws Exception {

//        System.out.println(Calendar.getInstance().getTime().toString());
//        String test = "aaaa";
//        System.out.println(test.getClass().getSimpleName());

//        Excel excel = new Excel(0);
////        int j = excel.getMaxColumnNumber();
////        int k = excel.getMaxRowNumber();
//        for (int i = 0; i <= 10; i++)
//            System.out.println(excel.readCell(1, i));
//
//        excel.writeCell("write", 7, 4);

//        excel.clear();

//        Class aClass = Class.forName("JunitBrowser");
//        Object object = aClass.newInstance();
//        System.out.println(object);

//        System.out.println(Test.class.getCanonicalName());
//
//        String mystring = "aaaaaa";
//        System.out.println(mystring.getClass());

//        System.out.println(System.getProperty("user.dir"));

//        Xml testxml = new Xml();
//        testxml.parserXml("src/com/auto/data/datasource.xml", "appium", "Android", "DU2TAN1576061867");
//        Collection collection = testxml.getSourceListTargetCollection();
//        Iterator iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next().toString());
//        }
//        Map<String, String> map = XmlUtils.getServerURL("server", "ip");
//        for (String key:map.keySet()) {
//            System.out.println(key + " " + map.get(key));
//        }

        System.out.println(XmlUtils.getServerURL("server", "ip"));

//        List<Map> list = testxml.getSourcesList();
//        String[][] appiumInfos = new String[list.size()][];
//
//        for (int i=0; i < list.size(); i++) {
//            Map<String, String> map = list.get(i);
//            String[] appiumInfo = new String[map.size()];
//            int j = 0;
//            for (String key:map.keySet()) {
////                if (key != "subject") {
//                System.out.println(key + " " + map.get(key) + "\n");
//                appiumInfo[j] = map.get(key);
//                j++;
////                }
//            }
//            appiumInfos[i] = appiumInfo;
//        }

//        SQL sql = new SQL("bussinessdb");
//        sql.operateDB();


    }

}
