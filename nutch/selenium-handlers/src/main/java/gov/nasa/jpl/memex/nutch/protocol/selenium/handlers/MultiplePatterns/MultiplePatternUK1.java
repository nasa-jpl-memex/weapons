
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
package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.MultiplePatterns;


        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.PrintWriter;
        import java.util.ArrayList;
        import java.util.Iterator;
        import java.util.List;
        import java.util.concurrent.TimeUnit;

        import org.apache.commons.lang.StringEscapeUtils;
        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.util.StringUtils;
        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

public class CS572LoginAndClickHandler implements InteractiveSeleniumHandler {
    private static final Logger LOG = LoggerFactory
            .getLogger(CS572LoginAndClickHandler.class);
    boolean refreshFlag = false;
    String tempAccumulatedData = "";
    ArrayList<String> accumulatedDataArrayList = new ArrayList<String>();
    String currentURL;

    public void processDriver(WebDriver driver) {
        try {
            Configuration conf = NutchConfiguration.create();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            currentURL = driver.getCurrentUrl();
            if (currentURL.contains("buyusedguns") && (currentURL.contains("login") || currentURL.contains("Login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDID(conf, driver, "username", "password", "submit", false);
            } else if (currentURL.contains("gunlistings") && (currentURL.contains("login") || currentURL.contains("Login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptNameNameXPath(conf, driver, "email_login", "password", "//input[@value='Login']", true);
            } else if (currentURL.contains("recycler") && (currentURL.contains("login") || currentURL.contains("Login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "login", "password", "//input[@value='Login']", true);
            } else if (currentURL.contains("slickguns") && (currentURL.contains("user"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDID(conf, driver, "edit-name", "edit-pass", "edit-submit", false);
                //
            } else if (currentURL.contains("shooting.org") && (currentURL.contains("login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "UserEmail", "UserPassword", "//input[@value='Sign in']", true);
                //
            } else if (currentURL.contains("wantaddigest.com") && (currentURL.contains("LogOn"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "Email", "Password", "//input[@value='Sign in']", true);
                //
            } else if (currentURL.contains("floridagunclassifieds.com") && (currentURL.contains("login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDID(conf, driver, "login_username", "login_password", "login", false);
                //
            } else if (currentURL.contains("gunidaho.com") && (currentURL.contains("login"))) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "User_email", "User_password", "//input[@value='Sign in']", true);
                //
            } else if (currentURL.equals("http://www.iwanna.com/")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDID(conf, driver, "welcome_username", "welcome_password", "welcome_login", false);
                //
            } else if (currentURL.contains("carolinabargaintrader.net/index.php") && currentURL.contains("a=10")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "username", "password", "//input[@value='Login']", false);
                //
            } else if (currentURL.contains("floridaguntrader.com/index.php") && currentURL.contains("a=10")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "username", "password", "//input[@value='Login >>']", false);
                //
            } else if (currentURL.contains("hawaiiguntrader.com") && currentURL.contains("login")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDID(conf, driver, "login_username", "login_password", "login", false);
                //
            } else if (currentURL.contains("theoutdoorstrader.com")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                WebElement username1 = driver.findElement(By.id("LoginControl"));
                WebElement password1 = driver.findElement(By.id("ctrl_password"));
                WebElement username2 = driver.findElement(By.id("ctrl_pageLogin_login"));
                WebElement password2 = driver.findElement(By.id("ctrl_pageLogin_password"));
                WebElement submitElement = driver.findElement(By.xpath("//input[@value='Log in']"));
                if (username1!=null && password1!=null && submitElement!=null) {
                    loginScriptIDIDXPath(conf, driver, "LoginControl", "ctrl_password", "//input[@value='Log in']", false);
                } else if (username2!=null && password2!=null && submitElement!=null) {
                    loginScriptIDIDXPath(conf, driver, "ctrl_pageLogin_login", "ctrl_pageLogin_password", "//input[@value='Log in']", false);
                }
                //
            } else if (currentURL.contains("zidaho.com/index.php") && currentURL.contains("a=10")) {
                System.out.println("[Interactiveselenium]: Interactive login \"" + currentURL + "\"");
                loginScriptIDIDXPath(conf, driver, "username", "password", "//input[@value='Login >>']", false);
                //
            } else if ( (currentURL.equals("http://www.budsgunshop.com/catalog/index.php"))
                    || (currentURL.equals("http://tennesseegunexchange.com/"))
                    || (currentURL.contains("gunidaho.com"))
                    || (currentURL.contains("hawaiiguntrader.com"))
                    || (currentURL.contains("slickguns.com"))
                    || (currentURL.contains("buyusedguns.net"))

                    ){
                System.out.println("[Interactiveselenium]: Interactive click \"" + currentURL + "\"");
                // Click link and record dynamic content
                clickAllAjaxLinks(conf, driver);
            } else {
                System.out.println("[Interactiveselenium]: Selenium only \"" + currentURL + "\"");
            }
        } catch (Exception e) {
            LOG.info(StringUtils.stringifyException(e));
        }
    }

    private void loginScriptIDIDXPath(Configuration conf, WebDriver driver, String usernameString, String passwordString, String XPathString, boolean isEmail) {
        new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
        WebElement usernameElement = driver.findElement(By.id(usernameString));
        WebElement passwordElement = driver.findElement(By.id(passwordString));
        WebElement submitElement = driver.findElement(By.xpath(XPathString));
        loginActionScript(driver, usernameElement, passwordElement, submitElement, isEmail);
    }

    private void loginScriptIDIDID(Configuration conf, WebDriver driver, String usernameString, String passwordString, String submitString, boolean isEmail) {
        new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
        WebElement usernameElement = driver.findElement(By.id(usernameString));
        WebElement passwordElement = driver.findElement(By.id(passwordString));
        WebElement submitElement = driver.findElement(By.id(submitString));
        loginActionScript(driver, usernameElement, passwordElement, submitElement, isEmail);
    }

    private void loginScriptNameNameXPath(Configuration conf, WebDriver driver, String usernameString, String passwordString, String XPathString, boolean isEmail) {
        new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
        WebElement usernameElement = driver.findElement(By.name(usernameString));
        WebElement passwordElement = driver.findElement(By.name(passwordString));
        WebElement submitElement = driver.findElement(By.xpath(XPathString));
        loginActionScript(driver, usernameElement, passwordElement, submitElement, isEmail);
    }



    private void loginActionScript(WebDriver driver, WebElement usernameElement, WebElement passwordElement, WebElement submitElement, boolean isEmail) {
        if (usernameElement!=null && passwordElement!=null && submitElement!=null) {
            if (isEmail) {
                usernameElement.sendKeys("cs572testtest@gmail.com");
            } else if (driver.getCurrentUrl().contains("carolinabargaintrader.net")) {
                usernameElement.sendKeys("cs572testtes");
            } else {
                usernameElement.sendKeys("cs572testtest");
            }

            if (driver.getCurrentUrl().contains("recycler.com")) {
                passwordElement.sendKeys("s572@testtest");
            } else {
                passwordElement.sendKeys("Cs572@test");
            }


            submitElement.click();
            try {
                Thread.sleep(2300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            recordHtmlBodyWithDynamicChange(driver);

            if (refreshFlag) {
                refreshFlag = false;
                // refresh page to refresh handler to the un-touched state
                driver.get(currentURL);
            }

            JavascriptExecutor jsx = (JavascriptExecutor) driver;
            Iterator<String> itr = accumulatedDataArrayList.iterator();
            while (itr.hasNext()) {
                String accumulatedData = itr.next();
                try {
                    jsx.executeScript("document.body.innerHTML = document.body.innerHTML + " + "'" + accumulatedData + "'" + ";");
                } catch (Exception ee) {
                    System.out.println(ee);
                }
            }
            // System.exit(0);
        }

    }

    private void clickAllAjaxLinks(Configuration conf, WebDriver driver) {
        int count = 0;
        new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
        List<WebElement> atags = driver.findElements(By.tagName("a"));
        int numberofajaxlinks = atags.size();
        for (int i = 0; i < numberofajaxlinks; i++) {

            if ((atags.get(i).getAttribute("href") != null)
                    && (atags.get(i).getAttribute("href").contains("javascript:void")
                    || atags.get(i).getAttribute("href").equals("javascript:;")
                    || (
                    atags.get(i).getAttribute("onclick") != null
                            && atags.get(i).getAttribute("onclick").contains("return false")
            )
            )

                    ) {

//			System.out.println(atags.get(i).getAttribute("onClick"));
                try {
                    atags.get(i).click();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    count ++;
                    recordHtmlBodyWithDynamicChange(driver);
                } catch (Exception e) {
                    LOG.info(StringUtils.stringifyException(e));
                    continue;
                }
            }

            if ((i == numberofajaxlinks - 1) || (count > 18)) { //Don't over-click
                JavascriptExecutor jsx = (JavascriptExecutor) driver;
                Iterator<String> itr = accumulatedDataArrayList.iterator();
                while (itr.hasNext()) {
                    String accumulatedData = itr.next();
                    try {
                        jsx.executeScript("document.body.innerHTML = document.body.innerHTML + " + "'" + accumulatedData + "'" + ";");
                    } catch (Exception ee) {
                        System.out.println(ee);
                    }
                }
                accumulatedDataArrayList.clear();
                break;
            }

            if (refreshFlag) {
                refreshFlag = false;
                // refresh page to refresh handler to the un-touched state
                driver.navigate().refresh();
                new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
                atags = driver.findElements(By.tagName("a"));
            }

        }
        //  System.exit(0);


    }

    private void recordHtmlBodyWithDynamicChange(WebDriver driver) {
        tempAccumulatedData = driver.findElement(By.tagName("body")).getAttribute("innerHTML");
        tempAccumulatedData = StringEscapeUtils.escapeJavaScript(tempAccumulatedData);
        accumulatedDataArrayList.add(tempAccumulatedData);
        refreshFlag = true;
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}
