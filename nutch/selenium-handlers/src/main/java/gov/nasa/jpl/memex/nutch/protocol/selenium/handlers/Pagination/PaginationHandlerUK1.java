

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



import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.lang.*;

public class PaginationHandlerUK1 implements InteractiveSeleniumHandler {

    public void processDriver(WebDriver driver) {}

    //Perform Login method
    public void doLogin(WebDriver driver,String username,String password,String usernameId,String passId,String submitId,boolean usernameByName,
                        boolean passByName,boolean submitByName,boolean isClick){
        System.out.println("Performing web page login");


        try{
            //Find the email field
            WebElement username_element = null;

            if(!usernameByName)
                username_element=driver.findElement(By.id(usernameId));
            else
                username_element=driver.findElement(By.name(usernameId));

            username_element.sendKeys(username);


            //Find the password field
            WebElement pass_element = null;


            if(!passByName)
                pass_element=driver.findElement(By.id(passId));
            else
                pass_element=driver.findElement(By.name(passId));

            pass_element.sendKeys(password);

            //Find the submit button
            WebElement submit_element = null;

            if(!submitByName)
                submit_element=driver.findElement(By.id(submitId));
            else
                submit_element=driver.findElement(By.name(submitId));

            if(isClick)
                submit_element.click();
            else
                submit_element.submit();

        }catch(Exception ex){
            System.out.println("Selinium Login error" + ex.getMessage());
        }

    }

    //Perform Pagination for navigating to next pages for more images
    public void doPagination(WebDriver driver,String URL){
        System.out.println("Performing Pagination");

        try{
            if (URL.contains("http://www.shooting.org/")){
                WebElement next_page=driver.findElement(By.xpath("//a[@rel='next']"));
                next_page.click();
            }else if(URL.contains("http://www.budsgunshop.com/catalog/")){
                WebElement next_page=driver.findElement(By.xpath("//a[@class='pageResults' and  @title=' Next Page ']"));
                next_page.click();
            }else if(URL.contains("http://www.nationalguntrader.com/")){
                WebElement next_page=driver.findElement(By.xpath("//a[contains(text(), 'Next')]"));
                next_page.click();
            }else if(URL.contains("http://www.shooterswap.com")){
                WebElement next_page=driver.findElement(By.className("moreads"));
                next_page.click();
            }else if(URL.contains()){
                WebElement next_page=driver.findElement(By.className("morebtn"));
                next_page.click();
            }


        }catch(Exception ex){
            System.out.println("Selinium Pagination error" + ex.getMessage());
        }
    }

    public boolean shouldProcessURL(String URL) {
        System.out.println("---------SAMI SELENIUM VERSION-----------");
        System.out.println(URL);
        WebDriver driver = new FirefoxDriver();
        driver.get(URL);

        //Handling of [HTTP 401 Responses] for Login
        if(URL.contains("https://www.impactguns.com/login.aspx")){
            doLogin(driver,"samidakhani@gmail.com","alibhai","ctl00_ctl00_MainContent_uxLogin_uxLogin_UserName","ctl00_ctl00_MainContent_uxLogin_uxLogin_Password",
                    "ctl00_ctl00_MainContent_uxLogin_uxLogin_ibLogin",false,false,false,true);
        }else if(URL.contains("http://floridaguntrader.com/index.php?a=10")){
            doLogin(driver,"samidakhani","alibhai","username", "password", "submit", false, false, true, false);
        }else if(URL.contains("http://www.gunidaho.com/site/login.htm")){
            doLogin(driver,"samidakhani@gmail.com","gpmvgeviczyi","User_email","User_password","btnLogin", false, false, true, false);
        }else if(URL.contains("http://www.idahogunsforsale.com/classifieds/index.php?a=10")){
            doLogin(driver,"samidakhani","alibhai","username","password","submit", false, false, true,false);
        }else if(URL.contains("http://www.kyclassifieds.com/login/")){
            doLogin(driver,"samidakhani","alibhai", "login_username", "login_password","login", true, true,true, false);
        }

        else if(URL.contains("page")){

            doPagination(driver, URL);

        }

    }
