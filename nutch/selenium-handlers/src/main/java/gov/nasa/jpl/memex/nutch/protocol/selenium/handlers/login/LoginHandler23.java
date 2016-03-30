
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


//TODO This group has similar handlers for other websites as well

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;


        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.By;
        import org.openqa.selenium.firefox.FirefoxDriver;

        import java.util.ArrayList;

public class LoginHandler23 implements InteractiveSeleniumHandler {
    private String a;

    private static final String domain="http://www.slickguns.com";

    private static final String username="csci572team23@gmail.com";
    private static final String password="csci572team23password";

    private static ArrayList<String> urls=new ArrayList<String>();

    static{
        urls.add("http://www.slickguns.com/users/reviewer");
        urls.add("http://www.slickguns.com/users/glock-fan");
    }


    public void processDriver(WebDriver driver) {
        if(driver.getTitle().equals("Access Denied / User Login | Slickguns")){
            if(a.equals("http://www.slickguns.com/users/reviewer")){
//                driver.get(a);
                driver.findElement(By.id("edit-name")).clear();
                driver.findElement(By.id("edit-name")).sendKeys("//username");
                driver.findElement(By.id("edit-pass")).clear();
                driver.findElement(By.id("edit-pass")).sendKeys("//password");
                driver.findElement(By.id("edit-submit")).click();
                //after log in, again go back to the page we wanted to be in
                driver.get(a);
            }else if(a.equals("http://www.slickguns.com/users/glock-fan")){
//                driver.get(domain + "/users/glock-fan");
                driver.findElement(By.id("edit-name")).clear();
                driver.findElement(By.id("edit-name")).sendKeys("//username");
                driver.findElement(By.id("edit-pass")).clear();
                driver.findElement(By.id("edit-pass")).sendKeys("password");
                driver.findElement(By.id("edit-submit")).click();
                //after log in, again go back to the page we wanted to be in
                driver.get(a);
            }else{
                return;
            }
        }else{
            return;
        }
    }

    public boolean shouldProcessURL(String URL) {
        if(urls.contains(URL.toLowerCase())){
            a=URL;
            return true;
        }else{
            return false;
        }
    }
}
