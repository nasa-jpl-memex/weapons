/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.misc;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.protocol.interactiveselenium.beans.*;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.TimeUnit;
        import java.util.regex.Pattern;
        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import org.openqa.selenium.By;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxProfile;
        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.Node;
        import org.w3c.dom.NodeList;

public class MiscHandler1 implements InteractiveSeleniumHandler {

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document doc;
    private static final String CONFIG_FILE = "conf/interactive-config.xml";

    private static List<Site> siteList;

    static {
        siteList = new ArrayList<Site>();
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(CONFIG_FILE);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Element root = doc.getDocumentElement();
        NodeList sites = root.getChildNodes();
        for(int i = 0; i < sites.getLength(); i++) {
            if(sites.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element site = (Element)sites.item(i);
                List<Action> actionList = new ArrayList<Action>();
                NodeList actions = site.getChildNodes();
                for(int j = 0; j < actions.getLength(); j++) {
                    if(actions.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element action = (Element)actions.item(j);
                        String type = action.getAttribute("type");
                        String by = action.getAttribute("by");
                        String value = action.getAttribute("value");
                        String key = action.getAttribute("key");
                        Action act = new Action(type, by, value, key);
                        actionList.add(act);
                    }
                }

                Site s = new Site(site.getAttribute("domain"), site.getAttribute("match"), actionList);
                siteList.add(s);


            }
        }

    }

    public void processDriver(WebDriver driver) {
        FirefoxProfile p = new FirefoxProfile();
        p.setPreference("webdriver_firefox_port", 8054);
        String URL = driver.getCurrentUrl();
        Site site = this.matches(URL);

        if(site == null) return ;

        System.out.println("Processing: " + URL);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        List<Action> actions = site.getActions();

        for(Action action: actions) {
            String actionType = action.getType();

            if(actionType.equals("input")) {
                WebElement element = findWebElement(action.getBy(), action.getValue(), driver);
                if(element == null) return;
                element.sendKeys(action.getKey());

            } else if(actionType.equals("click")) {
                WebElement element = findWebElement(action.getBy(), action.getValue(), driver);
                if(element == null) return;
                element.click();
                //Wait for a potential new page to load
                //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                //	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            } else if(actionType.equals("redirect-append")) {
                //driver.navigate().to(action.getValue());
                //driver.get(action.getValue());//Exactly same as the above

                driver.get(URL + action.getValue());

                //Wait for a potential new page to load
				/* {
					Thread.sleep(10000);
				} catch(Exception e) {
					e.printStackTrace();
				}*/
            }
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        }
        driver.close();

    }

    public boolean shouldProcessURL(String URL) {
        //System.out.println("Processing: " + URL);
        return true;
    }

    private WebElement findWebElement(String searchMethod, String searchValue, WebDriver driver) {
        WebElement element = null;
        try {
            if(searchMethod.equals("id")) {
                element = driver.findElement(By.id(searchValue));
            } else if(searchMethod.equals("name")) {
                element = driver.findElement(By.name(searchValue));
            } else if(searchMethod.equals("linkText")) {
                element = driver.findElement(By.linkText(searchValue));
            } else if(searchMethod.equals("xpath")) {
                element = driver.findElement(By.xpath(searchValue));
            }
        } catch(NoSuchElementException e) {
            System.out.println("No webelement found...");

            //e.printStackTrace();
        }
        return element;
    }

    //Return the matched site config
    private Site matches(String URL) {
        for(Site s : siteList) {

            String matchType = s.getMatch();

            if(matchType.equals("regex-match")) {
                //regex is stored in domain attribute
                Pattern p = Pattern.compile(s.getDomain());
                if(p.matcher(URL).matches())
                    return s;
            } else if(matchType.equals("exact-match")) {
                if(URL.equals(s.getDomain()))
                    return s;
            } else if(matchType.equals("contains")) {
                if(URL.contains(s.getDomain()))
                    return s;
            }

        }
        return null;
    }

}
