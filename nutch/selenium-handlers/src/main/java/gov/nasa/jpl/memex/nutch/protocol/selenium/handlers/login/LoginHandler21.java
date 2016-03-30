
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

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;


        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.io.File;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginHandler21 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {}

    public boolean shouldProcessURL(String URL) {
        System.out.print("============SELENIUM=================");
        System.out.println("DEBUG:::::"+ URL);
        System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox");
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get(URL);
        try
        {

            String url1,url2,url3,url4;

            url1 = "www.arguntrader.com/ucp.php?mode=login";
            url2 = "www.buyusedguns.net/login.php";
            url3 = "www.hawaiiguntrader.com/login/";
            url4 = "www.gunidaho.com/site/login.htm";

            WebElement username;
            WebElement password;
            WebElement submit;

            if (URL.contains(url1)) {
                System.out.println( url1+" :***************************login page***********************************************8");
                username = driver.findElement(By.id("username"));
                username.sendKeys("bhanuprasad");
                password = driver.findElement(By.id("password"));
                password.sendKeys("lasvegas");
                submit = driver.findElement(By.name("login"));
                submit.submit();
            }
            else if (URL.contains(url2)) {
                System.out.println( url2+" :***************************login page***********************************************8");
                username = driver.findElement(By.id("username"));
                username.sendKeys("bhanuirprasadir");
                password = driver.findElement(By.id("password"));
                password.sendKeys("bhanu@prasad");
                submit = driver.findElement(By.id("submit"));
                submit.submit();
            }

            else if (URL.contains(url3)) {
                System.out.println( url3 +" :***************************login page***********************************************8");
                username = driver.findElement(By.id("login_username"));
                username.sendKeys("bhanuir.prasadir@gmail.com");
                password = driver.findElement(By.id("login_password"));
                password.sendKeys("bhanu@prasad");
                submit = driver.findElement(By.id("login"));
                submit.submit();
            }

            else if (URL.contains(url4)) {
                System.out.println( url4+" :***************************login page***********************************************8");
                username = driver.findElement(By.id("User_email"));
                username.sendKeys("bhanuir.prasadir@gmail.com");
                password = driver.findElement(By.id("User_password"));
                password.sendKeys("vcytaybmabzk");
                submit = driver.findElement(By.name("btnLogin"));
                submit.submit();
            }
            else if (URL.contains("page")) {
                System.out.println(URL+" :************************pagination page***********************************************8");
                if (URL.contains("http://www.shooting.org/"))
                    driver.findElement(By.xpath("//a[@rel='next']"));
                else if(URL.contains("https://www.alphacatarmory.com/"))
                    driver.findElement(By.className("next"));

                else if(URL.contains("http://www.budsgunshop.com/catalog/"))
                    driver.findElement(By.xpath("//a[@class='pageResults' and  @title=' Next Page ']"));
            }
            else if ((URL.contains("www.store.kimberamerica.com/customer/account/login")) || (URL.contains("www.palmettostatearmory.com/index.php/customer/account/login/")) || (URL.contains("www.osagecountyguns.com/customer/account/login/")) || (URL.contains("www.sogknives.com/customer/account/login/")) || (URL.contains("www.budsgunshop.com/catalog/login.php")))
            {
                System.out.println( "***************************login page***********************************************8");
                WebElement email_element = driver.findElement(By.id("email"));
                email_element.sendKeys("las567vegas@gmail.com");
                WebElement pass_element = driver.findElement(By.id("pass"));
                pass_element.sendKeys("lasvegas");
                WebElement submit_element = driver.findElement(By.id("send2"));
                submit_element.submit();
            }

        }
        catch(Exception e) {
            driver.quit();
            System.out.println("In exception!!!" + e);

        }
        return true;
    }
}




