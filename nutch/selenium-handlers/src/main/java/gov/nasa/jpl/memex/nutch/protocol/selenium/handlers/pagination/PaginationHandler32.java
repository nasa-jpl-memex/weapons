

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

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.Pagination;

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.By;

        import java.net.URI;
        import java.util.List;


public class PaginationHandler32 implements InteractiveSeleniumHandler {
    private String xpath;

    public static final String URL_IMPACT_GUNS = "impactguns.com";
    public static final String IMPACT_GUNS_XPATH = "//*[@id=\"ctl00_ctl00_MainContent_uxCategory_uxCategoryProductList_BotNextLink\"]";

    public static final String URL_GUNS_BROKER = "gunbroker.com";
    public static final String GUNS_BROKER_XPATH = "//*[@id=\"pagerNext\"]";

    public static final String URL_GUNS_AD = "http://www.ncgunads.com/";
    public static final String GUNS_AD_XPATH = "//*[@id=\"classifiedsContainer\"]/table/tbody/tr/td[2]/table[2]/tbody/tr/td[1]/div/div";

    public static final String URL_DALLAS_GUNS = "http://www.dallasguns.com";
    public static final String DALLAS_GUNS_XPATH = "//*[@id=\"pagination\"]/span/a[4]";

    private setXpath(String host) {
        switch host {
            case URL_IMPACT_GUNS:
                this.xpath = IMPACT_GUNS_XPATH;
                break;

            case URL_GUNS_BROKER:
                this.xpath = GUNS_BROKER_XPATH;
                break;

            case URL_GUNS_AD:
                this.xpath = GUNS_AD_XPATH;
                break;

            case URL_DALLAS_GUNS:
                this.xpath = DALLAS_GUNS_XPATH;
                break;


            default:
                break;
        }
    }
    public List<HtmlAnchor> processDriver(WebDriver driver) {

        //Fetch the current URL and find the host and set the xpath for the pagenation
        String originalURL = driver.getCurrentUrl();
        URI uri = new URI(originalURL);
        String host = uri.getHost();
        setXpath(host)

        //Open the page using GET and find the 'Next' button using the xpath
        driver.get(originalURL);
        WebElement pagination = driver.findElements(By.xpath(xpath));
        List<HtmlAnchor> allAnchors = new ArrayList<HtmlAnchor>();
        while (null != pagination) {
            //Retrieve all the anchor tags from the page and add it into a list
            HtmlPage htmlPage = new HtmlPage(originalURL);
            List<HtmlAnchor> anchors = htmlPage.getAnchors();
            allAnchors.addAll(anchors);

            pagination.click();
        }
        //Return the list of anchors that is collected to HttpResponse.
        //We have modified the HttpResponse class to accept a list of anchor tags and hence add these URLs collected from the anchor tags to the Nutch db unfetched URL list
        return allAnchors;
    }
    public boolean shouldProcessURL(String URL) {
        return true;
    }
}
