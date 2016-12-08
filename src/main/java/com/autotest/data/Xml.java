package com.autotest.data;

import com.autotest.utils.LogRecord;

import org.apache.logging.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;


/**
 * Created by batcl on 10/15/2016.
 */
public class Xml {

    private Logger logger = LogRecord.getLogger();

    private Document document = null;

    private String usage = null;
    private String target = null;
    private String info = null;

    private List<Map<String,String>> sourcesList = new ArrayList<Map<String, String>>();

    public Xml() {

    }

    /*
    When using database(db), info is useless
    When using appium, info is used to identify the device
     */
    private void initKeyword(String usage, String target, String info) {
        this.usage = usage;
        this.target = target;
        this.info = info;
    }

    private void loadXml(String file_path) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file_path);
        } catch (SAXException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /*
    The nodelist getChildNodes() returns will treate the \n as a Node
    So need to drop it
     */
    private void parserXml() {
        // root
        NodeList rootNodeList = document.getChildNodes();
        Element rootElement = (Element) rootNodeList.item(0);

        // first level for usage
        NodeList firstNodeList = rootElement.getElementsByTagName("first");
        for (int i = 0; i < firstNodeList.getLength(); i++) {
            Element firstElement = (Element) firstNodeList.item(i);
            if (firstElement.getAttribute("usage").equals(this.usage)) {

                // second level for target
                NodeList secondNodeList = firstElement.getElementsByTagName("second");
                for (int j = 0; j < secondNodeList.getLength(); j++) {
                    Element secondElement = (Element) secondNodeList.item(j);

                    // Use a Map to store needed information, then add it to a List
                    Map<String, String> sources = new TreeMap<>();
                    if (secondElement.getAttribute("target").equals(this.target)) {
                        sources.put("target", this.target); // db or page
                        if (secondElement.getAttribute("ssh") != "" && secondElement.getAttribute("ssh") != null) {
                            sources.put("ssh", secondElement.getAttribute("ssh"));
                        }

                        // third level for info
                        NodeList thirdNodeList = secondElement.getElementsByTagName("third");
                        for (int k = 0; k < thirdNodeList.getLength(); k++) {
                            Element thirdElement = (Element) thirdNodeList.item(k);
                            sources.put(thirdElement.getAttribute("info"), thirdElement.getTextContent()); //db and page's info follow them
                        }
                    }
                    this.sourcesList.add(j, sources);
                }
            }
        }
    }


    public void parserXml(String file_path, String usage, String target, String info) {
        initKeyword(usage, target, info);
        try {
            loadXml(file_path);
            if (document != null) {
                parserXml();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /*
    Bellow are the return xml values' method.
    All these methods work base on correct data source
     */

    // Use this when there are more then one results
    public List<Map<String, String>> getSourcesList() { return  this.sourcesList; }

    // Use this when there is only one result and return map
    public Map<String, String> getTargetMap() {

        for (int i = 0; i < this.sourcesList.size(); i++) {
            if (this.sourcesList.get(i).get("target").equals(this.target)) {
                return this.sourcesList.get(i);
            }
        }

        return this.sourcesList.get(0);
    }

    // Use this when there are more then one results which return with object arrays
    public Object[][] getObjects() {
        String[][] Objects = new String[this.sourcesList.size()][];

        for (int i=0; i < this.sourcesList.size(); i++) {
            Map<String, String> map = this.sourcesList.get(i);
            String[] singleObject = new String[map.size()];
            int j = 0;
            for (String key:map.keySet()) {
                singleObject[j] = map.get(key);
                j++;
            }
            Objects[i] = singleObject;
        }

        return Objects;
    }

    public Object[][] getTargetObject(String info) {
        String[][] targetObject = new String[1][];

        for (int i=0; i < this.sourcesList.size(); i++) {
            Map<String, String> map = this.sourcesList.get(i);
            if(map.get(info).equals(this.info)) {
                System.out.println(map.size());
                String[] singleObject = new String[map.size()];
                int j = 0;
                for (String key:map.keySet()) {
                    System.out.println(key + ": " + map.get(key));
                    singleObject[j] = map.get(key);
                    j++;
                }
                targetObject[0] = singleObject;
                break;
            }
        }

        return targetObject;
    }

    // Use this to get the only one result's value
    public String getTargetValue() {
//        Map<String, String> map = getTargetMap();
//        return map.get(this.info);
        return getTargetMap().get(this.info);
    }





/*

    // This is only for Appium that has one device whick returns a object array
    public Object[][] getDeviceTargetObject() {
        String[][] targetObject = new String[1][];

        for (int i=0; i < this.sourcesList.size(); i++) {
            Map<String, String> map = this.sourcesList.get(i);
            if(map.get("deviceName").equals(this.info)) {
                System.out.println(map.size());
                String[] singleObject = new String[map.size()];
                int j = 0;
                for (String key:map.keySet()) {
                    System.out.println(key + ": " + map.get(key));
                    singleObject[j] = map.get(key);
                    j++;
                }
                targetObject[0] = singleObject;
                break;
            }
        }

        return targetObject;
    }

    // This is only for Appium that has one device which returns a Collection
    public Collection getSourceListTargetCollection() {
        Collection<String> collection = null;
        for (int i=0; i < this.sourcesList.size(); i++) {
            Map<String, String> map = this.sourcesList.get(i);
            if(map.get("deviceName").equals(this.info)) {
                collection = map.values();
            }
        }
        return collection;
    }

    // This is only for JunitBrowser which returns a object array
    public Object[][] getBrowserTargetObject() {
        String[][] targetObject = new String[1][];

        for (int i=0; i < this.sourcesList.size(); i++) {
            Map<String, String> map = this.sourcesList.get(i);
            if(map.get("browser").equals(this.info)) {
                System.out.println(map.size());
                String[] singleObject = new String[map.size()];
                int j = 0;
                for (String key:map.keySet()) {
                    System.out.println(key + ": " + map.get(key));
                    singleObject[j] = map.get(key);
                    j++;
                }
                targetObject[0] = singleObject;
                break;
            }
        }

        return targetObject;
    }
*/

}
