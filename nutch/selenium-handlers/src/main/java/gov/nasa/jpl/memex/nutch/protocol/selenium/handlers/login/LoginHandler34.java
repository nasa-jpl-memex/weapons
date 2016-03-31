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


        import java.util.List;

        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.util.StringUtils;
        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.Cookie;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;


public class LoginHandler34 implements InteractiveSeleniumHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ArguntraderHandler.class);

    public void processDriver(WebDriver driver) {
        try{
            String accumulatedData = "";
            driver.findElement(By.tagName("body")).getAttribute("innerHTML");
            Configuration conf = NutchConfiguration.create();
            new WebDriverWait(driver, 5);

            WebElement username = driver.findElement(By.id("username"));
            username.sendKeys("412651408@qq.com");
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("z123456");
            WebElement autologin = driver.findElement(By.id("autologin"));
            if(!autologin.isSelected()) {
                autologin.click();
            }
            WebElement login = driver.findElement(By.name("longin"));
            login.click();
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.className("forabg")));

            // Cookie cookie1 = new Cookie("phpbb3_63hz5_sid", "aa31814a45de62c5aa231456f190bf7a");
            // driver.manage().addCookie(cookie1);
            // Cookie cookie2 = new Cookie("phpbb3_63hz5_u", "12328");
            // driver.manage().addCookie(cookie2);
            // Cookie cookie3 = new Cookie("phpbb3_63hz5_k", "");
            // driver.manage().addCookie(cookie3);
            // Cookie cookie4 = new Cookie("style_cookie", "null");
            // driver.manage().addCookie(cookie4);
            // JavascriptExecutor jsx = (JavascriptExecutor) driver;
            // jsx.executeScript("document.body.innerHTML=document.body.innerHTML " + accumulatedData + ";");


        } catch (Exception e){
            LOG.info(StringUtils.stringifyException(e));
        }
    }

    public boolean shouldProcessURL(String URL) {
        // if(URL.equals("http://www.arguntrader.com")){
        //     return true;
        // }
        return true;
    }
}