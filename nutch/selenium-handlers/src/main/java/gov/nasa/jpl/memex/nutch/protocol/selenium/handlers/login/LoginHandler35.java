
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


 import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.util.StringUtils;
 import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
 import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.WebDriver;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.*;

public class LoginHandler35 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {

        String cur=driver.getCurrentUrl();
        if (cur.contains("/members/") || cur.contains("slickguns.com/alerts/")){
            Configuration conf = NutchConfiguration.create();
            new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay",
                    3));  // Wait for the page to load
            try{
                WebElement uname = driver.findElement(By.cssSelector("input[type=\"text\"]"));  // handling multiple text boxes not needed for these pages

                WebElement remember = driver.findElement(By.cssSelector("input[type=\"checkbox\"]"));  WebElement pwd = driver.findElement(By.cssSelector("input[type=\"password\"]"));
                uname.sendKeys("team35csci572@outlook.com");  // login info for all 403s

                pwd.sendKeys("qwer1234");
                remember.click();  // Check Remember Me option :)

                pwd.submit();
            }
            catch(Exception e){
            }
            driver.navigate().refresh();
//        new WebDriverWait(driver, 5)) // wait for click and any ending client process to compete execution (we are being generous here)


        }driver.close();
    }

    public boolean shouldProcessURL(String URL) {

        return true;
    }
}

