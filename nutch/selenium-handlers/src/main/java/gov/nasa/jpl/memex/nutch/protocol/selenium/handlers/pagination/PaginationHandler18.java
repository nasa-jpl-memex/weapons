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

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.pagination;


        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.TimeUnit;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.FluentWait;
        import org.openqa.selenium.support.ui.Select;
        import com.google.common.base.Predicate;

public class PaginationHandler18 implements InteractiveSeleniumHandler {
    public static boolean processFirstIwana = true;
    public static boolean processDavidSonic = true;
    public static ArrayList<String> impactGunList = new ArrayList<String>();

    // Dynamic processing for arms-list.com
    // This function handles pagination present in the web page by clicking throught all the pages. After clicking through
    // each page it collects the corresponding anchor tags present in it and loads it to the document body using the javascript
    // executor The newly loaded page is fetched by the nutch for further crawling.
    public void processArmsList(WebDriver driver)
    {
        int agreeSize=0;
        agreeSize = driver.findElements(By.xpath("/html//div[@class='ui-dialog-buttonset']/button[1]")).size();
        if(agreeSize > 0)
        {
            WebElement iAgree = driver.findElement(By.xpath("/html//div[@class='ui-dialog-buttonset']/button[1]"));
            iAgree.click();
        }
        int size = driver.findElements(By.xpath("html/body//div[@class='pager']//li[@class='next']/a")).size();
        if(size>0)
        {
            String str1="<html><body><p>";
            while(size > 0)
            {
                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                size = driver.findElements(By.xpath("html/body//div[@class='pager']//li[@class='next']/a")).size();
                if(size > 0)
                {
                    driver.findElement(By.xpath("html/body//div[@class='pager']//li[@class='next']/a")).click();
                }
            }
            str1+="</p></body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
    }
    // Dynamic processing for impactguns.com
    // This function handles pagination present in the web page by clicking throught all the pages. After clicking through
    // each page it collects the corresponding anchor tags present in it and loads it to the document body using the javascript
    // executor The newly loaded page is fetched by the nutch for further crawling.
    public void processImpactGuns(WebDriver driver)
    {
        if(impactGunList.contains(driver.getCurrentUrl()))
            return;
        impactGunList.add(driver.getCurrentUrl());
        WebElement nextBut;
        int size = driver.findElements(By.xpath("html/body//a[@id='ctl00_ctl00_MainContent_uxCategory_uxCategoryProductList_TopNextLink']")).size();
        if(size > 0)
        {
            String str1="<html><body><p>";
            while(size > 0)
            {
                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                size = driver.findElements(By.xpath("html/body//a[@id='ctl00_ctl00_MainContent_uxCategory_uxCategoryProductList_TopNextLink']")).size();
                if(size > 0)
                {
                    nextBut = driver.findElement(By.xpath("html/body//a[@id='ctl00_ctl00_MainContent_uxCategory_uxCategoryProductList_TopNextLink']"));
                    String disabledText = nextBut.getAttribute("disabled");
                    if(disabledText==null)
                        driver.findElement(By.xpath("html/body//a[@id='ctl00_ctl00_MainContent_uxCategory_uxCategoryProductList_TopNextLink']")).click();
                    else
                        break;
                }
            }
            str1+="</p></body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
    }

    //Dynamic processing for iwana.com
    //This function clicks on the next page button after entering the location zipcode
    public void processIwana(WebDriver driver)
    {
        int size = driver.findElements(By.xpath("html/body//input[@id='location']")).size();
        if(size > 0)
        {
            driver.findElement(By.xpath("html/body//input[@id='location']")).sendKeys("90007");
            driver.findElement(By.xpath("html/body//div[@class='mt10'][1]/a")).click();;
        }
        size = driver.findElements(By.xpath("html/body//div[@id='footer']/div[1]/a[3]")).size();

        if(size>0 && processFirstIwana == true)
        {
            driver.findElement(By.xpath("html/body//div[@id='footer']/div[1]/a[3]")).click();
            processFirstIwana = false;
        }
        size = driver.findElements(By.xpath("html/body//div[@id='paging']//a[@class='next']")).size();
        if(size > 0)
        {
            driver.findElement(By.xpath("html/body//div[@id='paging']//a[@class='next']")).click();
        }
    }

    //Dynamic processing for budgunshop.com
    // This function clicks on the next page button
    public void processBudgunshop(WebDriver driver)
    {
        int size = driver.findElements(By.xpath("html/body//form[@id='formcompare']/table[1]//a[@title=' Next Page ']")).size();
        if(size > 0)
        {
            driver.findElement(By.xpath("html/body//form[@id='formcompare']/table[1]//a[@title=' Next Page ']")).click();
        }
    }

    //Dynamic processing for buyusedguns.com
    // This function clicks on the '>' button - loads the next page
    public void processBuyusedguns(WebDriver driver)
    {
        ArrayList<WebElement> elemList = (ArrayList<WebElement>) driver.findElements(By.xpath("html/body//div[@class='pageNav'][1]//td[@class='paginationNum']/a"));
        if (elemList.size() > 0)
        {
            for(WebElement e : elemList)
            {
                String q=e.getText();
                if (q.equals(">"))
                {
                    e.click();
                    break;
                }
            }
        }
    }

    //Dynamic processing for Cheaperthan.com
    // Selects drop down as 'show all' by which all the images are displayed at once on the web page.
    public void processCheaperthandirt(WebDriver driver)
    {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.until( new Predicate<WebDriver>() {
                        public boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                        }
                    }
        );
        int size = driver.findElements(By.id("ctl00_ctl00_ContentPlaceHolderTopLevel_ContentPlaceHolderItemList_displayTopDDL")).size();
        if ( size > 0)
        {
            Select dropdown = new Select(driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolderTopLevel_ContentPlaceHolderItemList_displayTopDDL")));
            dropdown.selectByVisibleText("Show All");
        }

    }

    //Clicks on next page
    public void process4chan(WebDriver driver)
    {
        int size = driver.findElements(By.xpath("/html/body//form[@class='pageSwitcherForm']/input[@value='Next']")).size();
        if(size>0)
        {
            WebElement next = driver.findElement(By.xpath("/html/body//form[@class='pageSwitcherForm']/input[@value='Next']"));
            next.click();
        }
    }


    public void processtenness(WebDriver driver)
    {

        int next24 = driver.findElements(By.xpath("/html/body//div[@id='cwv3_enter']/a")).size();
        if (next24>0){
            WebElement next1 = driver.findElement(By.xpath("/html/body//div[@id='cwv3_enter']/a"));
            next1.click();
        }

        int next23 = driver.findElements(By.xpath("/html/body//div[@id='block1']/div[@class='paging']/a")).size();
        if (next23>0)
        {
            WebElement next2 = driver.findElement(By.xpath("/html/body//div[@id='block1']/div[@class='paging']/a"));
            next2.click();
        }

        String text1 = driver.findElement(By.xpath("/html/body//div[@class='pages']/span[@class='current']")).getText();
        int str1 = Integer.parseInt(text1);
        int test1 = driver.findElements(By.xpath("/html/body//div[@class='pages']/a[" + str1 + "]")).size();
        if(test1>0){
            WebElement next2w =  driver.findElement(By.xpath("/html/body//div[@class='pages']/a[" + str1 + "]"));
            next2w.click();
        }

    }

    // handling pagination for zidaho.com
    public void processzidaho(WebDriver driver)
    {
        int test1 = 8;
        int test2 = driver.findElements(By.xpath("/html/body//ul[@class='pagination browsing_result_page_links']/li[" + test1 + "]/a")).size();
        if(test2>0){
            WebElement next2w =  driver.findElement(By.xpath("/html/body//ul[@class='pagination browsing_result_page_links']/li[" + test1 + "]/a"));
            next2w.click();
        }
    }

    // handling pagination for dallasguns.com
    public void processdallasguns(WebDriver driver){
        String str1="<html><body><p>";
        while(true){
            int size = driver.findElements(By.xpath("/html/body//div[@id='pagination']/span/a[@title='Next Page']")).size();
            if(size > 0){

                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                //List<WebElement> ele2 = driver.findElements(By.xpath("html/body//img"));
                for(WebElement we :ele){
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                /*
                for(WebElement we : ele2){
                    str1+="<a href='"+(String)we.getAttribute("src")+"'>"+(String)we.getAttribute("src")+"</a><br>";
                } */
                WebElement ele1 = driver.findElement(By.xpath("/html/body//div[@id='pagination']/span/a[@title='Next Page']"));
                //System.out.println(ele1.getAttribute("href"));
                ele1.click();
            }
            else
                break;
        }
        str1+="</p></body></html>";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.innerHTML=arguments[0]",str1);
    }

    // handling pagination for elpaso.com
    public void processelpaso(WebDriver driver){


        String str1="<html><body><p>";
        while(true){
            int size = driver.findElements(By.xpath("/html/body//div[@class='art-pager']/a[@title='Next']")).size();
            if(size > 0){

                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                // List<WebElement> ele2 = driver.findElements(By.xpath("html/body//img"));
                for(WebElement we :ele){
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                /*
                for(WebElement we : ele2){
                    str1+="<a href='"+(String)we.getAttribute("src")+"'>"+(String)we.getAttribute("src")+"</a><br>";
                }
                */
                WebElement ele1 = driver.findElement(By.xpath("/html/body//div[@class='art-pager']/a[@title='Next']"));
                //System.out.println(ele1.getAttribute("href"));
                ele1.click();
            }
            else
                break;
        }
        str1+="</p></body></html>";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.innerHTML=arguments[0]",str1);
    }

    //handling pagination for floridaguns.com
    public void processfloridagun(WebDriver driver){
        String str1="<html><body><p>";
        while(true){
            String s = driver.findElement(By.xpath("/html/body//ul[@class='pagination browsing_result_page_links']/li[@class='current']")).getText();
            List<WebElement> ls = driver.findElements(By.xpath("/html/body//ul[@class='pagination browsing_result_page_links']/li/a"));
            WebElement wval = null;
            for(WebElement w1 : ls){
                String wstr=w1.getText();
                if (wstr.matches("[0-9]+")){
                    if(Integer.parseInt(wstr) == (Integer.parseInt(s)+1)){
                        wval=w1;
                        break;
                    }
                }
            }
            if(wval != null){
                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                //List<WebElement> ele2 = driver.findElements(By.xpath("html/body//img"));
                for(WebElement we :ele){
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                /*
                for(WebElement we : ele2){
                    str1+="<a href='"+(String)we.getAttribute("src")+"'>"+(String)we.getAttribute("src")+"</a><br>";
                }
                */
                wval.click();
            }
            else
                break;
        }
        str1+="</p></body></html>";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.innerHTML=arguments[0]",str1);
    }


    //handling pagination for igun.com
    public void processigun(WebDriver driver){
        String str1="<html><body><p>";
        while(true){
            int size = driver.findElements(By.xpath("/html/body//li/a[@class='searchPaginationNext list-last']")).size();
            if(size>0){
                List<WebElement> ele = driver.findElements(By.xpath("html/body//a"));
                //List<WebElement> ele2 = driver.findElements(By.xpath("html/body//img"));
                for(WebElement we :ele){
                    str1+="<a href='"+(String)we.getAttribute("href")+"'>"+(String)we.getAttribute("href")+"</a><br>";
                }
                /*
                for(WebElement we : ele2){
                    str1+="<a href='"+(String)we.getAttribute("src")+"'>"+(String)we.getAttribute("src")+"</a><br>";
                }
                */
                WebElement ele1 = driver.findElement(By.xpath("/html/body//li/a[@class='searchPaginationNext list-last']"));
                ele1.click();
            }
            else
                break;
        }
        str1+="</p></body></html>";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.innerHTML=arguments[0]",str1);
    }

    public void processgander(WebDriver driver)
    {
        int sizet = driver.findElements(By.xpath("/html/body//p[@class='page-numbers']/a")).size();
        if(sizet>0)
        {
            WebElement test2 = driver.findElement(By.xpath("/html/body//p[@class='page-numbers']/a"));
            String str1 = test2.getAttribute("onclick");
            ((JavascriptExecutor)driver).executeScript(str1);
        }
    }

    // select "All States" from dropdown and click on search button which gives list of results
    // Pagination is also handled after clicking search button
    public void processGunListingsOhio(WebDriver driver)
    {
        try
        {
            Select select_item = new Select(driver.findElement(By.name("state")));
            select_item.selectByVisibleText("All States");
            WebElement element = driver.findElement(By.xpath("//*[contains(@name,'search')]"));
            element.click();
            int page = 1;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    // check if the next page is present
                    String pagestr = "page="+ page;
                    String s1 = "a[href*='" + pagestr +"']";
                    WebElement page_num = driver.findElement(By.cssSelector(s1));
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    // end of pagination
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // pagination not present
        }
    }

    // Click on search button which gives list of results
    // Pagination is also handled after clicking search button
    public void processGunListings(WebDriver driver)
    {
        try
        {
            WebElement element = driver.findElement(By.xpath("//input[@src='images/search.jpg']"));
            element.click();
            int page = 1;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    // check if the next page is present
                    String pagestr = "page="+ page;
                    String s1 = "a[href*='" + pagestr +"']";
                    WebElement page_num = driver.findElement(By.cssSelector(s1));
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // search box not present
            // ex.printStackTrace();
        }
    }

    // handled pagination for wikiarms.com

    public void processWikiArms(WebDriver driver)
    {
        try
        {
            int page = 1;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    // check if the next page is present
                    WebElement page_num = driver.findElement(By.xpath("//a[text()='" + page + "']"));
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    // end of pagination
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // Pagination not present
        }
    }

    // handling pagination for Nextechclassifieds
    public void processNextechClassifieds(WebDriver driver)
    {
        try
        {
            int page = 1;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    // check if the next page is present
                    WebElement page_num = driver.findElement(By.xpath("//a[text()='" + page + "']"));
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    // end of (view more listings)
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // No pagination
        }
    }

    // function which keeps licking on "view more ads" button to fetch more ads
    public void processShooterSwap(WebDriver driver)
    {
        try
        {
            int page = 1;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    WebElement page_num;
                    if(page > 2)
                    {
                        page_num = driver.findElement(By.xpath("//a[text()=' View More Ads ']"));
                    }
                    else
                    {
                        page_num = driver.findElement(By.xpath("//a[text()='View More Ads']"));
                    }
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    // end of gun listings
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // View More Ads not recognized
        }
    }

    // handled pagination for slickguns webisite

    public void processSlickGuns(WebDriver driver)
    {
        try
        {
            int page = 0;
            String str1="<html><body><p>";
            while(true)
            {
                page++;
                List<WebElement> ele = driver.findElements(By.tagName("a"));
                for(WebElement we :ele)
                {
                    str1+="<a href='"+(String)we.getAttribute("href")+">"+(String)we.getAttribute("href")+"</a><br>";
                }
                try
                {
                    // check if the next page is present
                    // WebElement page_num = driver.findElement(By.xpath("//a[text()='"+ page +"']"));
                    String pagestr = "page="+ page;
                    String s1 = "a[href*='" + pagestr +"']";
                    WebElement page_num = driver.findElement(By.cssSelector(s1));
                    page_num.click();
                }
                catch(NoSuchElementException ex)
                {
                    // end of pagination
                    break;
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            str1+="</p></body></html>";
            js.executeScript("document.body.innerHTML=arguments[0]",str1);
        }
        catch (NoSuchElementException ex)
        {
            // no pagination found
        }
    }

    // handling login for arguntrader.com
    public void argunTrader(WebDriver driver) {
        String URL = driver.getCurrentUrl();
        driver.manage().window().maximize();
        //To click the login button in the home screen
        if (URL.equals("http://www.arguntrader.com/")) {
            driver.findElement(By.xpath("/html/body//li[@class='icon-logout']/a")).click();
        }
        //To enter the login details
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("char12");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("charan");
        //To click the login button
        driver.findElement(By.xpath("/html/body//dd/input[@class='button1']")).click();
    }

    //handling dynamic ajax calls for vci-classifieds.com
    public void vci_classifieds(WebDriver driver) {

        driver.manage().window().maximize();
        //to hover over the Ad Category button and click it
        driver.findElement(By.xpath("/html/body//a[@id='categorylink']")).click();
        //Hover over to firearm category and click Handguns
        WebElement element1 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/ul/li[4]/a"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element1);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> w1 = driver.findElements(By.xpath("html/body//div[@id='adlist']/table[3]//a"));
        for(WebElement ele:w1)
        {
            String str1;
            str1 = ele.getAttribute("href");
            int begin=str1.indexOf("(")+2;
            if(begin < 0)
                continue;
            int end=str1.indexOf(")")-1;
            if(end<0)
                continue;
            String adId = str1.substring(begin, end);
            String url = "https://www.vci-classifieds.com/a-getad.php?id=" + adId;
            executor.executeScript("$.get(arguments[0], function(data) { $('#fb-root').html(data); } );",url);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }

        element1 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/ul/li[5]/a"));
        executor.executeScript("arguments[0].click();", element1);
    }

    public void processDriver(WebDriver driver) {
        String url = driver.getCurrentUrl();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // Charan
        if(url.matches("^http://([a-zA-Z0-9]+.)*armslist.com/classifieds/\\S*"))
        {
            processArmsList(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*impactguns.com/\\S+.aspx"))
        {
            processImpactGuns(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*iwanna.com/\\S*"))
        {
            processIwana(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*budsgunshop.com/\\S*"))
        {
            processBudgunshop(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*buyusedguns.net/\\S*"))
        {
            processBuyusedguns(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*cheaperthandirt.com/\\S*"))
        {
            processCheaperthandirt(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*davidsonsinc.com/\\S*") && processDavidSonic )
        {
            int size=driver.findElements(By.xpath("html/body//div[9]/a")).size();
            if (size > 0)
            {
                driver.findElement(By.xpath("html/body//div[9]/a")).click();
            }
            processDavidSonic=false;
        }
        //charan ends
        //sharan
        if(url.matches("^http://([a-zA-Z0-9]+.)*boards.4chan.org/k/\\S*"))
        {
            process4chan(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*tennesseegunexchange.com/\\S*"))
        {
            processtenness(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*zidaho.com/category/(\\S*gun\\S*)|(\\S*Rifle\\S*)|(\\S*Ammunition\\S*)|(\\S*arms\\S*)|(\\S*Black-Powder\\S*)"))
        {
            processzidaho(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*dallasguns.com/\\S*"))
        {
            processdallasguns(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*elpasoguntrader.com/\\S*"))
        {
            processelpaso(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*floridaguntrader.com/\\S*"))
        {
            processfloridagun(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*iguntrade.com/\\S*"))
        {
            processigun(driver);
        }
        if(url.matches("^http://([a-zA-Z0-9]+.)*gandermountain.com/guns/\\S*"))
        {
            processgander(driver);
        }
        // sharan ends
        // rakshith
        if(url.matches("^http://([a-zA-Z0-9]+.)*gunlistings.org/ohio-gun-classifieds/\\S*"))
        {
            processGunListingsOhio(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*wikiarms.com/\\S*"))
        {
            processWikiArms(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*nextechclassifieds.com/\\S*"))
        {
            processNextechClassifieds(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*shooterswap.com/\\S*"))
        {
            processShooterSwap(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*slickguns.com/\\S*"))
        {
            processSlickGuns(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*gunlistings.org/\\S*"))
        {
            processGunListings(driver);
        }
        // rakshith ends
        // Mithun
        if(url.matches("^http://([a-zA-Z0-9]+.)*arguntrader.com/\\S*"))
        {
            argunTrader(driver);
        }

        if(url.matches("^http://([a-zA-Z0-9]+.)*vci-classifieds.com/\\S*"))
        {
            vci_classifieds(driver);
        }
        // Mithun ends
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}