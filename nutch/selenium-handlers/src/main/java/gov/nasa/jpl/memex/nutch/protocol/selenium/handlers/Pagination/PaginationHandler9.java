

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

        import java.util.regex.*;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.*;
        import org.openqa.selenium.htmlunit.HtmlUnitDriver;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.apache.hadoop.conf.Configuration;
        import java.util.List;
        import java.util.ArrayList;

public class PaginationHandler9 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {
        List<String> accumulatedData = new ArrayList<>();   //used to keep image tags from each page after click
        boolean paginationFound=false;
        boolean nextPageFound=false;

        System.err.println("DallasGunsPaginationHandler: Entered default dallasguns.com/guns_online page!!!!!!!!!,");
        String startPage = driver.getCurrentUrl();

        driver.findElement(By.tagName("body")).getAttribute("innerHTML");
        Configuration conf = NutchConfiguration.create();

        try{
            long end = System.currentTimeMillis() + 5000;
            while (System.currentTimeMillis() < end) {
                //Check if this page has pagination element, if so, need to process
                List<WebElement> testDivs = null;
                testDivs = driver.findElements(new By.ById("pagination"));
                if(testDivs!=null&&testDivs.size()>0){
                    WebElement paginationTest = testDivs.get(0);
                    if(paginationTest!=null){
                        paginationFound=true;
                        System.err.println("DallasGunsPaginationHandler: "+driver.getCurrentUrl()+" found pagination!!!!!!!!!!!!!!!!");
                        break;
                    }
                }
            }

            WebElement nextPage=null;
            List<WebElement> paginationDivs=null;
            List<WebElement> links=null;
            WebElement span=null;
            if(paginationFound){
                paginationDivs = driver.findElements(new By.ById("pagination"));
                if(paginationDivs.size()>0){
                    span = paginationDivs.get(0).findElement(new By.ByTagName("span"));
                    if(span!=null){
                        // System.err.println("DallasGunsPaginationHandler: found span!!!!!");

                        links =span.findElements(new By.ByTagName("a"));
                        if(links!=null&&links.size()>0){
                            System.err.println("DallasGunsPaginationHandler: found pagination links!!!!!");
                            nextPage = links.get(links.size()-1);
                            if(nextPage.getAttribute("title").equals("Next Page")){
                                System.err.println("DallasGunsPaginationHandler: found Next Page link at "+driver.getCurrentUrl()+"!!!!!!");
                                nextPageFound=true;
                            }
                        }
                    }
                }
            }

            while(nextPageFound){
                nextPage.click();
                new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
                System.err.println("DallasGunsPaginationHandler: Arrived at new URL: "+driver.getCurrentUrl()+" from click!!!!!!!" );

                List<WebElement> imgs = driver.findElements(new By.ByTagName("img")) ;

                if(imgs!=null&&imgs.size()>0){
                    System.err.println("DallasGunsPaginationHandler: Found new Images at "+driver.getCurrentUrl()+"!!!!!!!" );
                    for(int j=0; j<imgs.size(); j++){
                        String newImgSrc = imgs.get(j).getAttribute("src");
                        //String newImageTag = "<a href='"+newImgSrc+"'> another gun img </a>";
                        accumulatedData.add(newImgSrc);
                        //accumulatedData+=newImageTag;
                        System.err.println("DallasGunsPaginationHandler: updated accumulatedData with + "+ newImgSrc+"!!!!!!!!!");
                    }
                }
                paginationDivs = driver.findElements(new By.ById("pagination"));
                if(paginationDivs.size()>0){
                    span = paginationDivs.get(0).findElement(new By.ByTagName("span"));
                    if(span!=null){
                        // System.err.println("DallasGunsPaginationHandler: found span!!!!!");
                        links =span.findElements(new By.ByTagName("a"));
                        if(links!=null&&links.size()>0){
                            System.err.println("DallasGunsPaginationHandler: found pagination links!!!!!");
                            nextPage = links.get(links.size()-1);
                            if(nextPage.getAttribute("title").equals("Next Page")){
                                System.err.println("DallasGunsPaginationHandler: found Next Page link at "+driver.getCurrentUrl()+"!!!!!!");
                                nextPageFound=true;
                            }
                            else{
                                nextPageFound=false;
                            }
                        }else{
                            nextPageFound=false;
                        }
                    }else{
                        nextPageFound=false;
                    }
                }else{
                    nextPageFound=false;
                }
            }



            if(accumulatedData.size()>0){    //append images data to driver so that it can be processed by parser
                //navigating back to original page
                driver.get(startPage);
                System.err.println("DallasGunsPaginationHandler: navigated back to start page "+driver.getCurrentUrl()+"!!!!!!");
                new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
                System.err.println("DallasGunsPaginationHandler: appending new data!!!!!!!!");
                JavascriptExecutor jsx = (JavascriptExecutor) driver;
                for(String src:accumulatedData){
                    jsx.executeScript("var aTag = document.createElement('a'); aTag.setAttribute('href',\""+src+"\"); aTag.innerText = \"gun image\"; document.body.appendChild(aTag);");
                    //jsx.executeScript("aTag.setAttribute('href','"+src+"');");
                    //jsx.executeScript("aTag.innerText = 'gun image');");
                    //jsx.executeScript("document.body.appendChild(aTag);");
                }

                //jsx.executeScript("document.body.innerHTML+= "+ accumulatedData+";");
            }


            System.err.println("DallasGunsIndexHandler: Finished Pagination Handler at "+driver.getCurrentUrl()+"!!!!!!!!!!!!");


        }catch(Exception e){
            System.err.println("ERROR: DallasGunsIndexHandler @"+driver.getCurrentUrl()+e.getMessage());
        }

    }

    public boolean shouldProcessURL(String URL) {
        //Look for pages with the following regex pattern->these pages are the ones that might have the pagination that we are processing here
        Pattern p = Pattern.compile("^http://www.dallasguns.com/guns_online/.*");
        Matcher m = p.matcher(URL);
        boolean b = m.matches();
        return b;
    }
}
