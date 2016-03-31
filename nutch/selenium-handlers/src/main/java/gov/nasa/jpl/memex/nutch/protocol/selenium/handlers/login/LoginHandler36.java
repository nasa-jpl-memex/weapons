
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

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;


        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Map;
        import java.util.Set;

/**
 * This handler detects and performs login authentication to sites which require it.
 * @author ThammeGowda N and team (Team-36)
 *
 */
public class LoginHandler36 implements InteractiveSeleniumHandler {

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
        public abstract void handle(WebDriver driver);
    }

    /**
     * A specialized login handler for http://www.arguntrader.com/
     */
    private static class ArgunTraderHandler extends DomainHandler {

        private static final Logger LOG = LoggerFactory.getLogger(ArgunTraderHandler.class);
        public static final String DOMAIN_NAME = "www.arguntrader.com";
        public static final int DELAY = 8*1000; //this site has redirects which takes time

        private String loginScript;

        public ArgunTraderHandler(String userName, String password) {
            super(userName, password, DOMAIN_NAME);
            this.loginScript =
                    "var formEl = document.getElementById('login');\n" +
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
        public void handle(WebDriver driver) {
            String url = driver.getCurrentUrl();
            //checking if there is a login form
            WebElement formElement = driver.findElement(By.xpath("//FORM[@id='login']"));
            if (formElement == null) {
                LOG.debug("SKIP:Cant find Login form in {}", url);
            } else {
                LOG.debug("Detected Login Form; Run login script {}", url);
                LOG.debug("Page source length before login {}", driver.getPageSource().length());
                JavascriptExecutor jsx = (JavascriptExecutor) driver;
                jsx.executeScript(loginScript);
                //After login server will prompt a page and redirect to actual page
                try {
                    new WebDriverWait(driver, DELAY).wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOG.debug("Current URL {}", driver.getCurrentUrl());
                LOG.debug("Page source length after login {}", driver.getPageSource().length());
            }
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
    public void processDriver(WebDriver driver) {
        String domainName = getDomainName(driver.getCurrentUrl());
        if (domainHandlers.containsKey(domainName)) {
            domainHandlers.get(domainName).handle(driver);
        } else if (jsRequiredDomains.contains(domainName)){
            // this url needs javascript to be executed.
            // Selenium does it by itself, so nothing more to be done here!
        }
    }

    /**
     * gets domain /host name of URL
     * @param urlString url string
     * @return domain/host name on success; <code>null</code> on failure
     */
    private String getDomainName(String urlString){
        try {
            //if there is an entry
            return new URL(urlString).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param urlString url to check for handler's interest
     * @return true if this handler is interested with the input url
     */
    @Override
    public boolean shouldProcessURL(String urlString) {
        String domainName = getDomainName(urlString);
        // True if this domain is registered for special treatment
        // or just the Javascript script execution is required
        return domainHandlers.containsKey(domainName)
                || jsRequiredDomains.contains(domainName);
    }
}

