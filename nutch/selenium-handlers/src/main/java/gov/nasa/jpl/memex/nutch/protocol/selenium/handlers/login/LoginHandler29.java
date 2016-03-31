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


        import org.openqa.selenium.*;
        import java.util.concurrent.TimeUnit;

public class LoginHandler29 implements InteractiveSeleniumHandler {

    public void processDriver(WebDriver driver) {
        // driver.manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS);
        String url = driver.getCurrentUrl();
        if (url.contains("outdoorstrader.com/members")){
            try {
                driver.findElement(By.id("ctrl_pageLogin_login")).clear();
                driver.findElement(By.id("ctrl_pageLogin_login")).sendKeys("csci572inforetteam29@gmail.com");
                driver.findElement(By.id("ctrl_pageLogin_password")).clear();
                driver.findElement(By.id("ctrl_pageLogin_password")).sendKeys("crawlparty");
                driver.findElement(By.cssSelector("input.button.primary")).click();
            }
            catch (Exception e) {

            }
        } else if (url.contains("arguntrader.com/viewforum")) {
            try {
                driver.findElement(By.id("username")).clear();
                driver.findElement(By.id("username")).sendKeys("gead");
                driver.findElement(By.id("password")).clear();
                driver.findElement(By.id("password")).sendKeys("crawlparty");
                driver.findElement(By.id("autologin")).click();
                driver.findElement(By.name("login")).click();
            }
            catch (Exception e) {

            }
        } else if (url.contains("backpage.com") && !url.contains("www")) {// hacky but should work
            try {
                driver.findElement(By.linkText("sports equip.")).click();
            } catch(Exception e) {

            }
        }
        driver.manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS);
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}
