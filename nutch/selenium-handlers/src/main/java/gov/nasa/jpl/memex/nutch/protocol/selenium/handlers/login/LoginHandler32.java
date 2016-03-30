
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


        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.By;

public class LoginHandler32 implements InteractiveSeleniumHandler {


    public void processDriver(WebDriver driver) {

        String OriginalURL = driver.getCurrentUrl();
        driver.get(OriginalURL);

        //Find the login button and click on it
        WebElement LoginButton = driver.findElement(By.linkText("Login"));
        LoginButton.click();

        //Find the username textbox using the name of the textbox - which is typically "Username"
        WebElement useremail = driver.findElement(By.linkText("Username"));

        //if username is not found try finding an element with "email"
        if (null == useremail) {
            useremail = driver.findElement(By.linkText("email"));
        }
        useremail.clear();
        useremail.sendKeys("cs572team32@gmail.com");

        WebElement password = driver.findElement(By.linkText("Password"));
        password.clear();
        password.sendKeys("ViewSonic");

        WebElement submitButton = driver.findElement(By.linkText("submit"));
        submitButton.click();
    }

    public boolean shouldProcessURL(String URL) {
        return true;
    }
}