
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
package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.PageNavigation;

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;


        import org.openqa.selenium.WebDriver;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.interactions.Actions;

public class PageNavigation3 implements InteractiveSeleniumHandler {
    private static final String USERNAME = "junpengl_usc";
    private static final String PASSWORD = "junpengl";
    private static final String[] PROCESS_URL = {"tennesseegunexchange.com",
            "armslist.com"};

    public void processDriver(WebDriver driver) {
        try {

            System.out.println("Page title is: " + driver.getTitle());

            String url = driver.getCurrentUrl();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            By targetBy = By.id("");
            By targetPath = By.id("");

            if (url.contains("armslist")) {
                targetBy = By.id("termsagreement");
                targetPath = By.xpath("//button[span='I Agree']");

                WebElement element = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(targetBy)
                );
                element = element.findElement(targetPath);
                element.click();
            } else if (url.contains("tennesseegunexchange")) {
                System.out.println("url contains msgun!!!!!");
                targetBy = By.id("cw_enter_link");

                WebElement ele = driver.findElement(targetBy);
                Actions action = new Actions(driver);

                action.moveToElement(ele).build().perform();
                ele.click();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //System.out.println("=====DEBUG: click success!");

    }

    public boolean shouldProcessURL(String URL) {
        for (String url : PROCESS_URL){
            if (URL.contains(url)) {
                return true;
            }
        }
        return false;
    }
}

