
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


        import java.util.List;

        import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.util.StringUtils;
        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.*;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

/**
 * This handler first clicks all the <a hfer="javascript:void(null);"> tags
 * because it considers them as not usual links but ajax links/interactions.
 * Then it submit keyword 'weapon' to search text to get the content behind
 * the form.
 */
public class PageNavigationUK2 implements InteractiveSeleniumHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicContentHandler.class);
    public void processDriver(WebDriver driver) {
        try {
            String accumulatedData = driver.findElement(By.tagName("body")).getAttribute("innerHTML");
            Configuration conf = NutchConfiguration.create();
            new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
            //handle ajax content
            List<WebElement> atags = driver.findElements(By.tagName("a"));
            int numberofajaxlinks = atags.size();
            for (int i = 0; i < numberofajaxlinks; i++) {
                if (atags.get(i).getAttribute("href") != null
                        && atags.get(i).getAttribute("href")
                        .equals("javascript:void(null);")) {
                    atags.get(i).click();
                    if (i == numberofajaxlinks - 1) {
                        //append everything to the driver in the last round
                        JavascriptExecutor jsx = (JavascriptExecutor) driver;
                        jsx.executeScript("document.body.innerHTML=document.body.innerHTML "
                                + accumulatedData + ";");
                        continue;
                    }
                    accumulatedData += driver.findElement(By.tagName("body"))
                            .getAttribute("innerHTML");
                    //refreshing the handlers as the page was interacted with
                    driver.navigate().refresh();
                    new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay",
                            3));
                    atags = driver.findElements(By.tagName("a"));
                }
            }
            //handle content behind a form
            List<WebElement> inputTags = driver.findElements(By.tagName("input"));
            int numberOfInput = inputTags.size();
            for (int i = 0; i < numberOfInput; i++) {
                if (inputTags.get(i).getAttribute("type") != null && inputTags.get(i).getAttribute("type").equals("text")) {
                    //take the first text input as the search input
                    inputTags.get(i).sendKeys("weapon");
                    inputTags.get(i).submit();
                    accumulatedData += driver.findElement(By.tagName("body")).getAttribute("innerHTML");
                    JavascriptExecutor jsxInput = (JavascriptExecutor) driver;
                    //append the data to the driver
                    jsxInput.executeScript("document.body.innerHTML=document.body.innerHTML " + accumulatedData + ";");
                    break;
                }
            }

        } catch (Exception e) {
            LOG.info(StringUtils.stringifyException(e));
        }
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}


