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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Search handler to find search fields and input search keys **/

public class SearchHandler implements InteractiveSeleniumHandler {

    private String searchWindowPathFromUrl(String url) {
        switch (url) {
            case "http://www.gowilkes.com/": return "//*[@id='search_box']";
            case "http://www.nextechclassifieds.com/" : return "//*[@id='nav-search-input']";
            default: return "";
        }
    }

    private String searchInputPathFromUrl(String url) {
        switch (url) {
            case "http://www.gowilkes.com/": return "//*[@id='search_box']";
            case "http://www.nextechclassifieds.com/" : return "//*[@id='nav-search-input']";
            default: return "";
        }
    }

    private String searchButtonPathFromUrl(String url) {
        switch (url) {
            case "http://www.gowilkes.com/" : return "//*[@id='search_container']/form/table/tbody/tr/td[2]/span";
            case "http://www.nextechclassifieds.com/" : return "//*[@id='search-btn']";
            default: return "";
        }
    }

    private String searchRadioButtonPath(String url) {
        switch (url) {
            case "http://www.gowilkes.com/" : return "//*[@id='search_container']/form/div/div[5]/input";
            case "http://www.nextechclassifieds.com/" : return "//*[@id='nav-search-input']";
            default: return "";
        }
    }

    private String searchKeyFromUrl(String url) {
        switch (url) {
            default: return "guns";
        }
    }

    private String genericSearchHandler(WebDriver webDriver, String htmlpage, String searchWindowPath, String searchInputPath, String searchButtonPath, String searchRadioButtonPath, String searchKey) {
        webDriver.manage().window().maximize();
        try {
            WebElement findSearchWindow = webDriver.findElement(By.xpath(searchWindowPath));
            if(findSearchWindow.isDisplayed()) findSearchWindow.click();
            WebElement classifiedRadioBtn = webDriver.findElement(By.xpath(searchRadioButtonPath));
            if(classifiedRadioBtn.isSelected()) classifiedRadioBtn.click();
            WebElement username = webDriver.findElement(By.xpath(searchInputPath));
            if(username.isDisplayed()) username.sendKeys(searchKey);
            WebElement loginBtn = webDriver.findElement(By.xpath(searchButtonPath));
            if(loginBtn.isDisplayed()) loginBtn.click();
            try {
                Thread.sleep(5000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            htmlpage += webDriver.findElement(By.tagName("body")).getAttribute("innerHTML");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return htmlpage;
    }

    public String processDriver (WebDriver webDriver){
        String htmlpage="";
        String currentUrl = webDriver.getCurrentUrl();
        String searchWindowPath = searchWindowPathFromUrl(currentUrl);
        String searchInputPath = searchInputPathFromUrl(currentUrl);
        String searchButtonPath = searchButtonPathFromUrl(currentUrl);
        String searchRadioButton = searchRadioButtonPath(currentUrl);
        String searchKey = searchKeyFromUrl(currentUrl);
        htmlpage += genericSearchHandler(webDriver, htmlpage, searchWindowPath, searchInputPath, searchButtonPath, searchRadioButton, searchKey);
        return htmlpage;
    }
    public boolean shouldProcessURL(String URL) {
        System.out.println("URL IS "+ URL);
        return true;
    }
}
