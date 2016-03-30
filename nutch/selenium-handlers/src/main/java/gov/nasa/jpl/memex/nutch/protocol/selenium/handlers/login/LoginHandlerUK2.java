
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

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.support.ui.Select;

public class LoginHandlerUK2 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {
        try {
            if (driver.getCurrentUrl().startsWith("http://abqcareers.careers.adicio.com/jobs/user/login")) {
                driver.findElement(By.id("auth_email")).sendKeys("csci572hw@gmail.com");
                driver.findElement(By.id("auth_password")).sendKeys("lalahaha");
                driver.findElement(By.id("submitForm")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://www.hawaiiguntrader.com/login")) {
                driver.findElement(By.id("login_username")).sendKeys("csci572hw");
                driver.findElement(By.id("login_password")).sendKeys("Rg^lW$ual&Ul");
                driver.findElement(By.id("login")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://buyusedguns.net/login")) {
                driver.findElement(By.id("username")).sendKeys("csci572hw");
                driver.findElement(By.id("password")).sendKeys("lalahaha");
                driver.findElement(By.id("submit")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://freegunclassifieds.com/user/login")) {
                driver.findElement(By.id("email")).sendKeys("csci572hw@gmail.com");
                driver.findElement(By.id("password")).sendKeys("lalahaha");
                driver.findElement(By.id("password")).submit();
            }

            if (driver.getCurrentUrl().startsWith("http://floridaguntrade.com/login")) {
                driver.findElement(By.id("login_username")).sendKeys("csci572hw");
                driver.findElement(By.id("login_password")).sendKeys("VDa3pD4OLxti");
                driver.findElement(By.id("login")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://www.montanagunclassifieds.com/login")) {
                driver.findElement(By.name("update_email")).sendKeys("csci572hw@gmail.com");
                driver.findElement(By.name("password")).sendKeys("lalahaha");
                driver.findElement(By.name("submit")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://www.nationalguntrader.com/main/login")) {
                driver.findElement(By.id("username")).sendKeys("csci572hw");
                driver.findElement(By.id("password")).sendKeys("14630382");
                driver.findElement(By.id("password")).submit();
            }

            if (driver.getCurrentUrl().startsWith("https://www.nextechclassifieds.com/myaccounts/login")) {
                driver.findElement(By.id("id_username")).sendKeys("csci572hw");
                driver.findElement(By.id("id_password")).sendKeys("lalahaha");
                driver.findElement(By.id("id_password")).submit();
            }

            if (driver.getCurrentUrl().startsWith("http://www.kyclassifieds.com/login")) {
                driver.findElement(By.id("login_username")).sendKeys("csci572hw");
                driver.findElement(By.id("login_password")).sendKeys("lalahaha");
                driver.findElement(By.id("login")).submit();
            }

            if ("http://www.kyclassifieds.com/".startsWith(driver.getCurrentUrl())) {
                driver.findElement(By.id("go")).click();
            }

            if (driver.getCurrentUrl().startsWith("http://www.gunlistings.org")) {
                driver.findElement(By.name("category")).submit();
            }

            if ("http://www.gunbroker.com/".startsWith(driver.getCurrentUrl())) {
                new Select(driver.findElement(By.id("Cats"))).selectByIndex(1);
                driver.findElement(By.id("pbSearch")).click();
            }

            if ("http://www.gandermountain.com/".startsWith(driver.getCurrentUrl())) {
                driver.findElement(By.id("search_form")).submit();
            }
        } catch (NoSuchElementException e) {
            System.out.println("MyHandler: " + e.toString());
        }
    }

    public boolean shouldProcessURL(String URL) {
        String[] prefixes = {
                "http://abqcareers.careers.adicio.com",
                "http://www.hawaiiguntrader.com",
                "http://buyusedguns.net",
                "http://freegunclassifieds.com",
                "http://floridaguntrade.com",
                "http://www.montanagunclassifieds.com",
                "http://www.nationalguntrader.com",
                "http://www.nextechclassifieds.com",
                "http://www.kyclassifieds.com",
                "http://www.gunbroker.com",
                "http://www.gandermountain.com"
        };
        for (int i = 0; i < prefixes.length; ++i) {
            if (URL.startsWith(prefixes[i])) {
                return true;
            }
        }
        return false;
    }
}