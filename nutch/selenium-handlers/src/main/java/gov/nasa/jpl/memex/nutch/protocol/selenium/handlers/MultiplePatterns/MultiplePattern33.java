
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
package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.MultiplePatterns;

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;

        import java.util.List;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.WebDriver;
        import org.apache.hadoop.conf.Configuration;
        import org.apache.nutch.protocol.selenium.HttpWebClient;
        import org.openqa.selenium.JavascriptExecutor;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;


public class CrawlHandler implements InteractiveSeleniumHandler {

    /**
     *  Writing htmlString to file
     */

    public void writeURLToFile(StringBuffer buffer){
        File f=new File("SeleniumUrls.txt");
        try{
            FileWriter fwriter = new FileWriter(f);
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(buffer.toString());
            bwriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  handles data behind web form, pagination, ajax based interaction for seed urls
     */

    public void processDriver(WebDriver driver) {
        StringBuffer sBuffer = new StringBuffer();
        String htmlpage="<html><body>";
        System.out.println("reading url "+driver.getCurrentUrl());
        if(driver.getCurrentUrl().equals("http://www.wikiarms.com/")){
            // handling pagination

            driver.manage().window().maximize();
            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }

            WebElement ele = driver.findElement(By.xpath("//*[@id='products-table']/div/ul//a[@rel='next']"));
            for(int i=0;ele!=null;i++){

                try{
                    ele.click() ;
                    System.out.println(driver.getCurrentUrl());
                    Thread.sleep(1000);
                    List<WebElement> anchorTags2 =driver.findElements(By.xpath("//*[@id='products-table']//a"));

                    for (WebElement anchorTag2 : anchorTags2){
                        htmlpage+="<a href='"+anchorTag2.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag2.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    ele = driver.findElement(By.xpath("//*[@id='products-table']/div/ul//a[@rel='next']"));

                }
                catch(Exception e){
                    ele = null;

                }

            }
            htmlpage+="</body></html>";
            System.out.println("html is "+htmlpage);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            //return htmlpage;


        }else if(driver.getCurrentUrl().equals("http://www.ksl.com/")){
            // handling pagination and search guns

            driver.manage().window().maximize();
            WebElement loginWindow=driver.findElement(By.xpath("//*[@id='kslHeader']/div/div[1]/div/div[4]/a/span"));
            loginWindow.click();
            WebElement username=driver.findElement(By.className("ksl-header-search__input"));
            username.sendKeys("guns");
            WebElement loginBtn=driver.findElement(By.xpath("//*[@id='kslHeader']/div/div[1]/div/div[4]/div/form/button"));
            loginBtn.click();
            System.out.println(driver.getCurrentUrl());
            List pages =driver.findElements(By.xpath("//*[@id='___gcse_0']/div/div/div/div[5]/div[2]/div/div/div[2]/div[11]//div"));
            int size= pages.size();
            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            System.out.println(size);
            if(size>0){
                for(int i=2; i<size; i++){
                    System.out.println("inside for loop");
                    try{
                        driver.findElement(By.xpath("//*[@id='___gcse_0']/div/div/div/div[5]/div[2]/div/div/div[2]/div[11]/div/div["+i+"]")).click() ;
                        Thread.sleep(1000);
                        List<WebElement> anchorTags2 =driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[2]/div[3]/div/div[2]//a"));

                        for (WebElement anchorTag2 : anchorTags2){
                            htmlpage+="<a href='"+anchorTag2.getAttribute("href")+"'>test</a>";
                            sBuffer.append(anchorTag2.getAttribute("href"));
                            sBuffer.append("\n");
                        }
                    }
                    catch(Exception e){

                    }
                }

            }

            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);

        }else if(driver.getCurrentUrl().equals("http://www.kyclassifieds.com/")){

            // handling pagination and search guns

            System.out.println("final else loop");
            driver.manage().window().maximize();
            WebElement searchField=driver.findElement(By.xpath("//*[@id='s']"));
            searchField.sendKeys("guns");
            WebElement searchButton=driver.findElement(By.xpath("//*[@id='go']"));
            searchButton.click();
            System.out.println(driver.getCurrentUrl());
            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            WebElement ele =driver.findElement(By.className("next page-numbers"));
            for(int i=0;ele!=null;i++){

                try{
                    ele.click() ;
                    System.out.println(driver.getCurrentUrl());
                    Thread.sleep(1000);
                    List<WebElement> anchorTags2 =driver.findElements(By.xpath("/html/body/div/div[4]/div/div/div[2]//a"));

                    for (WebElement anchorTag2 : anchorTags2){
                        htmlpage+="<a href='"+anchorTag2.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag2.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    ele = driver.findElement(By.className("next page-numbers"));

                }
                catch(Exception e){
                    ele = null;

                }

            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);


        }else if(driver.getCurrentUrl().equals("http://www.iguntrade.com/")){
            // handling paginations

            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            WebElement ele =driver.findElement(By.className("searchPaginationNext list-last"));
            for(int i=0;ele!=null;i++){

                try{
                    ele.click() ;
                    System.out.println(driver.getCurrentUrl());
                    Thread.sleep(1000);
                    List<WebElement> anchorTags2 =driver.findElements(By.xpath("//*[@id='main']//a"));

                    for (WebElement anchorTag2 : anchorTags2){
                        htmlpage+="<a href='"+anchorTag2.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag2.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    ele = driver.findElement(By.className("searchPaginationNext list-last"));

                }
                catch(Exception e){
                    ele = null;

                }

            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);


        }else if(driver.getCurrentUrl().equals("http://www.gowilkes.com/")){
            // handling search guns

            WebElement searchField=driver.findElement(By.xpath("//*[@id='search_box']"));
            searchField.sendKeys("guns");
            WebElement classifiedRadioBtn=driver.findElement(By.xpath("//*[@id='search_container']/form/div/div[5]/input"));
            classifiedRadioBtn.click();
            WebElement searchButton=driver.findElement(By.xpath("//*[@id='search_container']/form/table/tbody/tr/td[2]/span"));
            searchButton.click();
            System.out.println(driver.getCurrentUrl());
            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            List<WebElement> anchorTagGuns=driver.findElements(By.xpath("//*[@id='content']/tbody/tr/td[1]/div[2]/table"));
            for (WebElement anchorTag : anchorTagGuns){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);


        }else if(driver.getCurrentUrl().equals("http://www.floridagunclassifieds.com/")){
            // handling scrolling of page

            for(int i=0;i<20;i++){
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,250)", "");
            }
            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);
        }else if(driver.getCurrentUrl().equals("http://www.elpasoguntrader.com/")){
            // handling pagination

            List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }

            WebElement nextField=driver.findElement(By.xpath("//*[@id='dj-classifieds']/div/div/div//a[@title='Next']"));
            for(int i=0;nextField.isEnabled();i++){
                try{
                    nextField.click();
                    Thread.sleep(1000);
                    List<WebElement> anchors =driver.findElements(By.xpath("//a"));

                    for (WebElement anchorTag : anchors){
                        htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    nextField=driver.findElement(By.xpath("//*[@id='dj-classifieds']/div/div/div//a[@title='Next']"));
                }
                catch(Exception e){
                }
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);


        }else if(driver.getCurrentUrl().equals("http://www.classifiednc.com/")){
            // handling gun link tap in left panel and pagination

            WebElement forSale=driver.findElement(By.xpath("//*[@id='body_con']/div[1]/div/div[2]/div[5]"));
            forSale.click();
            WebElement gunList=driver.findElement(By.xpath("//*[@id='catlist']/div[16]/a"));
            gunList.click();
            boolean nextBtn=driver.findElement(By.xpath("//*[@id='classifiedsContainer']/table/tbody/tr/td[2]/table[2]/tbody/tr/td/div/div")).isDisplayed();
            for(int i=0;nextBtn!=false;i++){
                try{
                    Thread.sleep(1000);
                    List<WebElement> anchorTags =driver.findElements(By.xpath("//*[@id='classifiedsContainer']/table/tbody/tr/td[2]/table[1]//a"));

                    for (WebElement anchorTag : anchorTags){
                        htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    driver.findElement(By.xpath("//*[@id='classifiedsContainer']/table/tbody/tr/td[2]/table[2]/tbody/tr/td/div/div")).click();
                    nextBtn=driver.findElement(By.xpath("//*[@id='classifiedsContainer']/table/tbody/tr/td[2]/table[2]/tbody/tr/td/div/div")).isDisplayed();
                }
                catch(Exception e){
                }
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);
        }else if(driver.getCurrentUrl().equals("http://www.wantaddigest.com/")){
            // handling search field
            WebElement forSale=driver.findElement(By.xpath("//*[@id='country']"));
            forSale.sendKeys("guns");
            WebElement nextBtn=driver.findElement(By.xpath("//*[@id='left-home']/form/input[2]"));
            try{
                nextBtn.click();
                Thread.sleep(1000);
                List<WebElement> anchorTags =driver.findElements(By.xpath("//*[@id='middle']//a"));
                for (WebElement anchorTag : anchorTags){
                    htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                    sBuffer.append(anchorTag.getAttribute("href"));
                    sBuffer.append("\n");
                }
            }
            catch(Exception e){
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);

        }else if(driver.getCurrentUrl().equals("http://www.nextechclassifieds.com/")){
            // handling search field

            WebElement searchField=driver.findElement(By.xpath("//*[@id='nav-search-input']"));
            searchField.sendKeys("guns");
            WebElement searchBtn=driver.findElement(By.xpath("//*[@id='search-btn']"));
            searchBtn.click();
            boolean nextBtn=driver.findElement(By.xpath("//*[@id='site-wrapper']/div/div[2]/div/div[2]/ul[1]/li[8]/a")).isEnabled();
            for(int i=0;nextBtn!=false;i++){
                try{
                    Thread.sleep(1000);
                    List<WebElement> anchorTags =driver.findElements(By.xpath("//*[@id='gallery-container']/ul//a"));
                    for (WebElement anchorTag : anchorTags){
                        htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    driver.findElement(By.xpath("//*[@id='site-wrapper']/div/div[2]/div/div[2]/ul[1]/li[8]/a")).click();
                    nextBtn=driver.findElement(By.xpath("//*[@id='site-wrapper']/div/div[2]/div/div[2]/ul[1]/li[8]/a")).isEnabled();
                }
                catch(Exception e){
                }
            }
            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);
        }else if(driver.getCurrentUrl().equals("http://www.theoutdoorstrader.com/")){
            // handling pagination and ajax interaction

            driver.manage().window().maximize();
            WebElement searchField=driver.findElement(By.xpath("//*[@id='QuickSearchQuery']"));
            searchField.click();
            searchField.sendKeys("guns");
            WebElement submitBtn=driver.findElement(By.xpath("//*[@id='QuickSearch']/form/div[2]/dl/dd/input"));
            submitBtn.click();
            List<WebElement> anchorTags =driver.findElements(By.xpath("//*[@id='content']/div/div//a"));
            for (WebElement anchorTag : anchorTags){
                htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                sBuffer.append(anchorTag.getAttribute("href"));
                sBuffer.append("\n");
            }

            boolean nextBtn=driver.findElement(By.linkText("Next >")).isDisplayed();

            for(int i=0;nextBtn!=false;i++){
                try{
                    Thread.sleep(1000);
                    driver.findElement(By.linkText("Next >")).click();
                    List<WebElement> anchors =driver.findElements(By.xpath("//*[@id='content']/div/div//a"));
                    for (WebElement anchorTag : anchors){
                        htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                        sBuffer.append(anchorTag.getAttribute("href"));
                        sBuffer.append("\n");
                    }
                    nextBtn=driver.findElement(By.linkText("Next >")).isDisplayed();
                }
                catch(Exception e){
                    nextBtn=false;
                }
            }

            htmlpage+="</body></html>";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.innerHTML=arguments[0]",htmlpage);
            System.out.println(htmlpage);
        }else if(driver.getCurrentUrl().equals("http://www.arguntrader.com/")){
            // handling web form data

            driver.manage().window().maximize();
            WebElement searchField=driver.findElement(By.xpath("//*[@id='page-header']/div[2]/div/ul[2]/li[3]/a"));
            searchField.click();
            WebElement usernameField=driver.findElement(By.xpath("//*[@id='username']"));
            usernameField.sendKeys("testing");
            WebElement passwordField=driver.findElement(By.xpath("//*[@id='password']"));
            passwordField.sendKeys("usccsci572");
            WebElement loginBtn=driver.findElement(By.xpath("//*[@id='login']/div[1]/div/div/fieldset/dl[4]/dd/input[3]"));
            loginBtn.click();
            try{
                Thread.sleep(5000);
                List<WebElement> anchorTags =driver.findElements(By.xpath("//a"));

                for (WebElement anchorTag : anchorTags){
                    htmlpage+="<a href='"+anchorTag.getAttribute("href")+"'>test</a>";
                    sBuffer.append(anchorTag.getAttribute("href"));
                    sBuffer.append("\n");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("else loop");
        }
        writeURLToFile(sBuffer);
    }

    /**
     * Processes the URL
     * @param  url  an absolute URL
     * @return boolean value true
     */

    public boolean shouldProcessURL(String URL) {
        System.out.println("URL IS "+URL);
        return true;
    }
}