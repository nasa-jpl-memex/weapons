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
package org.apache.nutch.protocol.interactiveselenium.handlers;


import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginHandler implements InteractiveSeleniumHandler {

    /**
     * Base class for all handlers
     */
    private static abstract class DomainHandler {
        /**
         * username, password and domain name are stored here
         */
        protected String userName;
        protected String password;
        protected String domainName;

        public DomainHandler(String userName, String password, String domainName) {
            this.userName = userName;
            this.password = password;
            this.domainName = domainName;
        }

        /**
         * Specialized handler for domain shall implement this method
         * @param driver - an instance of selenium web driver
         */
        public abstract String handle(WebDriver driver, String webContent);
    }

    /**
     * A specialized login handler for http://www.arguntrader.com/
     */
    private static class ArgunTraderHandler extends DomainHandler {

        private static final Logger LOG = LoggerFactory.getLogger(ArgunTraderHandler.class);
        public static final String DOMAIN_NAME = "http://arguntrader.com/";
        public static final int DELAY = 8*1000; //this site has redirects which takes time

        private String loginScript;

        // Just retaining this script for future reference for design which is otherwise unused.

        public ArgunTraderHandler(String userName, String password) {
            super(userName, password, DOMAIN_NAME);
            this.loginScript =
                    "var formEl = document.getElementByName('login');\n" +
                            "if (formEl != null){\n" +
                            "   var unInput = formEl.querySelector('#username');\n" +
                            "   var pwInput = formEl.querySelector('#password');\n" +
                            "   unInput.value='%s';\n" +
                            "   pwInput.value='%s';\n" +
                            "   loginBtn = formEl.querySelector('input[name=\\'login\\']');\n" +
                            "   loginBtn.click();\n" +
                            "}";
            // update username and password
            this.loginScript = String.format(this.loginScript, userName, password);
        }


        @Override
        public String handle(WebDriver driver, String webContent) {
            driver.manage().window().maximize();
            WebElement searchField=driver.findElement(By.xpath("//*[@id='page-header']/div[2]/div/ul[2]/li[3]/a"));
            searchField.click();
            WebElement usernameField=driver.findElement(By.xpath("//*[@id='username']"));
            usernameField.sendKeys("testing");
            WebElement passwordField=driver.findElement(By.xpath("//*[@id='password']"));
            passwordField.sendKeys("usccsci572");
            WebElement loginBtn=driver.findElement(By.xpath("//*[@id='login']/div[1]/div/div/fieldset/dl[4]/dd/input[3]"));
            loginBtn.click();
            try{
                Thread.sleep(5000);
                java.util.List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));
                for (WebElement anchorTag : anchorTags){
                    webContent+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return webContent;
        }
    }

    /**
     * authHandlers for domains which require authentication. These domains wont let the unauthenticated client
     * view content.
     * Mappings of  DomainName -> handler
     */
    private static Map<String, DomainHandler> domainHandlers = new HashMap<String, DomainHandler>();
    /**
     * Set of domains which change view at client side using javascript execution.
     * These domains don't need authentication to view content.
     */
    private static Set<String> jsRequiredDomains = new HashSet<String>();
    static {
        //loading handlers
        domainHandlers.put(ArgunTraderHandler.DOMAIN_NAME, new ArgunTraderHandler("tgn", "tgn*#123"));
        //more handlers like this here.

        // adding sites which change view at client sites.
        jsRequiredDomains.add("www.hipointfirearmsforums.com");
        //more domains like this here
    }

    /**
     * Invokes a registered domain handler for handling the web page
     * @param driver selenium web driver instance
     */
    @Override
    public String processDriver(WebDriver driver) {
        String domainName = driver.getCurrentUrl();
        String accumulatedData = "";
        if(shouldProcessURL(domainName)) {
            accumulatedData += domainHandlers.get(domainName).handle(driver, accumulatedData);
        }
        // // TODO: 4/17/16 change return type of parent method to generic type and return void
        return accumulatedData;
    }
    /**
     * @param urlString url to check for handler's interest
     * @return true if this handler is interested with the input url
     */
    @Override
    public boolean shouldProcessURL(String urlString) {
        return domainHandlers.containsKey(urlString)
                || jsRequiredDomains.contains(urlString);
    }
}
