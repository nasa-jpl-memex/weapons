
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
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginHandlerUK4 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {}

    public boolean shouldProcessURL(String URL) {
        System.console().writer().println("Nutch Crawler CSCI 572");
        String url1 =  "www.sogknives.com/product/register";
        String url2 = "palmettostatearmory.com/index.php/customer/account/login/";

        if(URL.contains(url1)){
            WebDriver webDriver = new FirefoxDriver();
            webDriver.get(URL);
            webDriver.findElement(By.xpath("//a[contains(.,'Log In')]")).click();
            webDriver.findElement(By.id("email")).sendKeys("mail2bigb_21@yahoo.co.in");
            webDriver.findElement(By.id("pass")).sendKeys("mail2bigb_21");
            webDriver.findElement(By.id("send2")).click();
        } else if (URL.contains(url2)) {
            WebDriver webDriver = new FirefoxDriver();
            webDriver.get(URL);
            webDriver.findElement(By.id("email")).sendKeys("mail2bigb_21@yahoo.co.in");
            webDriver.findElement(By.id("pass")).sendKeys("mail2bigb_21");
            webDriver.findElement(By.id("send2")).click();
        } else {
            WebDriver webDriver = new FirefoxDriver();
            webDriver.get(URL);
        }
        return true;
    }
}
