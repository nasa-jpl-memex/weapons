
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

        import org.openqa.selenium.WebDriver;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.JavascriptExecutor;

public class LoginHandler3 implements InteractiveSeleniumHandler {
    private static final String USERNAME = "junpengl_usc";
    private static final String PASSWORD = "junpengl";
    private static final String PROCESS_URL = "www.theoutdoorstrader.com/members";

    public void processDriver(WebDriver driver) {
        try {
            WebElement element = driver.findElement(By.name("login"));

            element.sendKeys(USERNAME);

            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys(PASSWORD);

            WebElement remember = driver.findElement(By.name("remember"));
            remember.click();

            // Now submit the form. WebDriver will find the form for us from the element
            element.submit();

            // Check the title of the page
            //System.out.println("Page title is: " + driver.getTitle());

            // Wait for the page to load, timeout after 10 seconds
            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    return (Boolean)js.executeScript("return jQuery.active == 0");
                }
            });
            //System.out.println("Page title is: " + driver.getTitle());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean shouldProcessURL(String URL) {
        if (URL.contains(PROCESS_URL)) {
            return true;
        }
        return false;
    }
}
