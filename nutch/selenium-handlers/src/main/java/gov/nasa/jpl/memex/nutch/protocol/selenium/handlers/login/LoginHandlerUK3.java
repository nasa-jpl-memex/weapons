
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

import org.apache.nutch.protocol.interactiveselenium.Http;


import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import java.util.concurrent.TimeUnit;
        import java.util.List;


public class LoginHandlerUK3 implements InteractiveSeleniumHandler {
    private Http http;
    public void processDriver(WebDriver driver) {}

    public boolean shouldProcessURL(String URL) {

        WebDriver driver = new FirefoxDriver();
        // to resolve selinum issue with Firefox
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        try
        {
            Thread.sleep(10000);
        }
        catch (Exception e)
        {
            Http.LOG.error("error in try dishant " + e);
        }

        Http.LOG.error("trigger" + URL);
        if(URL.equals("http://www.arguntrader.com"))
        {
            Http.LOG.error("in" + URL);
            // login for arguntrader.com
            driver.get("http://www.arguntrader.com/ucp.php?mode=login");
            WebElement query = driver.findElement(By.id("username"));
            query.sendKeys("testtest");
            query = driver.findElement(By.id("password"));
            query.sendKeys("password123");
            driver.findElement(By.name("login")).click();
        }
        else if(URL.equals("http://www.iguntrade.com"))
        {
            Http.LOG.error("in" + URL);
            // pagination for iguntrade.com
            driver.get("http://www.iguntrade.com/index.php?page=search");
            List<WebElement> allpages = driver.findElements(By.xpath("//div[@class='paginate']//a"));
            for(int i=0; i<=(allpages.size()); i++)
            {
                allpages.get(i).click();

            }
        }
        else if(URL.contains("http://www.zidaho.com/category"))
        {
            Http.LOG.error("in" + URL);
            // pagination for zidaho.com
            List<WebElement> allpages = driver.findElements(By.xpath("//ul[@class='pagination browsing_result_page_links']//a"));
            for(int i=0; i<=(allpages.size()); i++)
            {
                allpages.get(i).click();

            }
        }

        return true;
    }
}
