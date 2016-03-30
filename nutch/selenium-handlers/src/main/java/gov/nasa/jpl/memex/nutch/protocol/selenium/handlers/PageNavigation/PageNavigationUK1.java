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


        import java.util.List;
        import java.util.regex.Pattern;

        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.WebDriverWait;

public class PageNavigationUK1 implements InteractiveSeleniumHandler {

    public void processDriver(WebDriver driver) {

        if(driver.getCurrentUrl().equalsIgnoreCase("http://www.academy.com/"))
            processDriverForAcademy(driver);
        else if(driver.getCurrentUrl().equalsIgnoreCase("http://www.gandermountain.com/"))
            processDriverForGandermountain(driver);
        else if(driver.getCurrentUrl().equalsIgnoreCase("http://www.hipointfirearmsforums.com/"))
            processDriverForHipointfirearmsforums(driver);
        else if(driver.getCurrentUrl().equalsIgnoreCase("http://www.iwanna.com/"))
            processDriverForIwanna(driver);
        else if(driver.getCurrentUrl().equalsIgnoreCase("http://www.lionseek.com/"))
            processDriverForLionseek(driver);
        else if(Pattern.matches("http://www.arguntrader.com/.*", driver.getCurrentUrl()))
            processDriverForArguntrader(driver);

    }

    /*
     * Navigating to an appropriate URL
     */
    public void processDriverForAcademy(WebDriver driver) {
        WebElement search = driver.findElement(By.id("item_shooting mainmenu-hunting-firearms"));
        driver.get(search.findElement(By.tagName("a")).getAttribute("href"));
    }

    /*
     * Navigating to the Guns URL only
     */
    public void processDriverForGandermountain(WebDriver driver) {
        List<WebElement> search = driver.findElements(By.className("top-level-link"));
        for(WebElement element : search){
            String link = element.getAttribute("href");
            if(link.contains("guns")){
                driver.get(link);
                return;
            }
        }
    }

    /*
     * Navigating to Gun Images only
     */
    public void processDriverForHipointfirearmsforums(WebDriver driver) {
        List<WebElement> search = driver.findElement(By.id("navigation")).findElements(By.tagName("a"));
        for(WebElement element : search){
            String link = element.getAttribute("href");
            if(link.contains("photo")){
                driver.get(link);
                return;
            }
        }
    }

    /*
     * Keyword based search and Page Navigation using Javascript Executor
     */
    public void processDriverForIwanna(WebDriver driver) {
        String data = "";
        String nextLink = "";
        String[] keywords = { "rifle", "hawk", "gun", "pistol",
                "firearm", "grenade", "bomb", "sniper", "sword", "knife", "knives",
                "flamethrower", "carbine", "revolver", "missile", "barrel",
                "bullet", "gunpowder", "muzzle", "trigger", "weapon", "ammo",
                "ammunition" };

        List<WebElement> elements = driver.findElement(By.id("tag_cloud")).findElements(By.tagName("a"));
        for(WebElement element : elements) {
            String link = element.getAttribute("href");
            LOOP: for(String key : keywords){
                if(link.contains(key)){
                    WebDriver travel = new FirefoxDriver();
                    travel.get(link);
                    while(true){
                        try{
                            List<WebElement> listings = travel.findElements(By.className("listing"));
                            for(WebElement listing : listings)
                                data += listing.findElement(By.className("column70")).findElement(By.tagName("a")).getAttribute("href") + " ";
                            JavascriptExecutor executor = (JavascriptExecutor)travel;
                            nextLink = travel.findElement(By.className("next")).getAttribute("onclick") + ";";
                            executor.executeScript(nextLink);
                        }
                        catch(Exception e){
                            travel.quit();
                            break LOOP;
                        }
                    }
                }
            }
        }
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.body.innerHTML=document.body.innerHTML + \"" + data + "\";");

    }

    /*
     * Keyword based link extraction and avoiding noise
     */
    public void processDriverForLionseek(WebDriver driver) {
        String data = "";
        boolean flag = false;
        String[] keywords = { "rifle", "hawk", "gun", "pistol",
                "firearm", "grenade", "bomb", "sniper", "sword", "knife", "knives",
                "flamethrower", "carbine", "revolver", "missile", "barrel",
                "bullet", "gunpowder", "muzzle", "trigger", "weapon", "ammo",
                "ammunition" };

        List<WebElement> elements = driver.findElements(By.className("category"));
        for(WebElement element : elements) {
            flag = false;
            String alt = element.findElement(By.tagName("img")).getAttribute("alt").toLowerCase();
            LOOP: for(String key : keywords){
                if(alt.contains(key)){
                    data += element.getAttribute("href") + " ";
                    flag = true;
                    break LOOP;
                }
            }
            if(!flag){
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("document.querySelector('a[title=\"" + element.getAttribute("title") + "\"]').setAttribute(\"href\", \"#\");");
            }

        }
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.querySelector('a[href=\"/sitemap\"]').setAttribute(\"href\", \"#\");");
        executor.executeScript("document.body.innerHTML=document.body.innerHTML + \"" + data + "\";");
    }

    /*
     * Bypass login authentication
     */
    public void processDriverForArguntrader(final WebDriver driver) {
        if(driver.getTitle().toLowerCase().contains("login")){
            WebElement username = driver.findElement(By.id("username"));
            WebElement password = driver.findElement(By.id("password"));
            username.sendKeys("shoot.dexter");
            password.sendKeys("Curious@1234");
            driver.findElement(By.className("button1")).click();

            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d.getCurrentUrl().toLowerCase().contains(driver.getCurrentUrl().toLowerCase());
                }
            });
        }
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}
