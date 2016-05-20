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

package org.apache.nutch.protocol.interactiveselenium.handlers;


import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PaginationHandler implements InteractiveSeleniumHandler {

    private String handle(WebDriver driver, String htmlpage) {
        for(int i=0;i<100;i++) {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
        }
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

        for (WebElement anchorTag : anchorTags){
            htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
        }
        WebElement ele =driver.findElement(By.className("searchPaginationNext"));
        while(ele != null) {
            try{
                ele.click() ;
                System.out.println(driver.getCurrentUrl());
                Thread.sleep(1000);
                List<WebElement> anchorTags2 =driver.findElements(By.xpath("//*[@id='main']//a"));

                for (WebElement anchorTag2 : anchorTags2){
                    htmlpage+="<a href='"+anchorTag2.getAttribute("href")+"'>test</a>";

                }
                ele = driver.findElement(By.className("searchPaginationNext"));

                for(int i=0;i<100;i++) {
                    JavascriptExecutor jse = (JavascriptExecutor) driver;
                    jse.executeScript("window.scrollBy(0,250)");
                }
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                ele = null;
            }
        }
        htmlpage+="</body></html>";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
        return htmlpage;
    }

    public String processDriver(WebDriver webDriver) {
        String htmlpage="";
        String currentUrl = webDriver.getCurrentUrl();
        switch (currentUrl) {
            case "http://www.iguntrade.com/index.php?page=search": htmlpage += handle(webDriver, htmlpage);
                break;
            default: throw new IllegalArgumentException("no method implemented for this url " + currentUrl);
        }
        return htmlpage;
    }

    public boolean shouldProcessURL(String URL) {
        System.out.println("URL IS "+URL);
        return true;
    }
}
