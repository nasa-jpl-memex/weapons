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

package org.apache.nutch.protocol.interactiveselenium;

        import org.openqa.selenium.By;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;


/**
 * @author Balaji
 *
 * This class is a selenium handler used to identify pages that contain login forms, populate the login credentials and submit the form.
 *
 */
public class LoginHandlerUK1 implements InteractiveSeleniumHandler {

    public static final Logger LOG = LoggerFactory.getLogger("org.apache.nutch.parse.html");
    public WaitForJS wait = new WaitForJS();

    @Override
    public String processDriver(WebDriver driver) {

        WebElement element;
        try {
            if ((element = driver.findElement(By.xpath("//form/input[@type=password]"))) != null) {
                WebElement formElement = element.findElement(By.xpath(".//.."));
                formElement.findElement(By.xpath(".//input[@type='password']")).sendKeys("//Their keys//");
                formElement.findElement(By.xpath(".//input[@type='text']")).sendKeys("//Their keys//");
                formElement.findElement(By.xpath(".//input[@type='submit' or @type='button']")).click();

            }
        } catch (NoSuchElementException e) {
            LOG.info("Page contains no login form.");
        }

        return driver.findElement(By.tagName("body")).getAttribute("innerHTML");
    }

    @Override
    public boolean shouldProcessURL(String URL) {
        return true;
    }

}