
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


public class LoginHandler38 implements InteractiveSeleniumHandler {
    public void processDriver(WebDriver driver) {}

    public boolean shouldProcessURL(String URL) {
        System.out.print("============SELENIUM=================");

        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get(URL);

        try
        {
            if ((URL == "https://store.kimberamerica.com/customer/account/login") || (URL == "https://palmettostatearmory.com/index.php/customer/account/login/") || (URL == "https://www.osagecountyguns.com/customer/account/login/") || (URL == "https://www.sogknives.com/customer/account/login/") || (URL == "https://www.budsgunshop.com/catalog/login.php"))
            {
                System.out.println( "***************************login page***********************************************8");
                WebElement email_element = driver.findElement(By.id("email"));
                email_element.sendKeys("las567vegas@gmail.com");
                WebElement pass_element = driver.findElement(By.id("pass"));
                pass_element.sendKeys("lasvegas");
                WebElement submit_element = driver.findElement(By.id("send2"));
                submit_element.submit();
            }

            else if (URL.contains("page"))
            {
                System.out.println("************************pagination page***********************************************8");
                if (URL.contains("http://www.shooting.org/"))
                    driver.findElement(By.xpath("//a[@rel='next']"));

                else if(URL.contains("https://www.alphacatarmory.com/"))
                    driver.findElement(By.className("next"));

                else if(URL.contains("http://www.budsgunshop.com/catalog/"))
                    driver.findElement(By.xpath("//a[@class='pageResults' and  @title=' Next Page ']"));
            }
        }
        catch(Exception e)
        {
            System.out.println("In exception!!!");

        }
        return true;
    }
}




