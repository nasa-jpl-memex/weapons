
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



        import java.util.List;
        import java.io.PrintWriter;
        import java.io.File;
        import java.io.*;
        import java.net.*;
        import java.lang.Object;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.firefox.FirefoxDriver;

        import org.openqa.selenium.interactions.Actions;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import java.nio.ByteBuffer;
        import java.nio.channels.Channels;
        import java.nio.channels.ReadableByteChannel;


        import org.openqa.selenium.WebDriver;

public class LoginHandler8 implements InteractiveSeleniumHandler {

    //WebDriver driver = new FirefoxDriver();

    // Go to the Google Suggest home page
    int i = 0;
    String url1;
    public void processDriver(WebDriver driver) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String lineContent;
        PrintWriter writer = null;
        String currentURL = null;

        driver.get(url1);

        try{
            writer = new PrintWriter("theFile" + i + ".txt", "UTF-8");
            i++;
        }catch (IOException ex) {
            System.out.println("Something wrong with the PrintWriter");
        }

        try {
            url = new URL(url1);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((lineContent = br.readLine()) != null && writer != null) {
                writer.println(lineContent);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
            }
        }
        if(writer != null){
            writer.close();
        }

        if(url1.equals("http://www.theoutdoorstrader.com")){
            driver.findElement(By.value("loginBar")).click();
            driver.findElement(By.id("LoginControl")).sendKeys("will");
            driver.findElement(By.id("ctrl_password")).sendKeys("will");
            driver.findElement(By.value("Log in")).click();
        }

        currentURL = driver.getCurrentUrl();
        try{
            writer = new PrintWriter("theFile" + i + ".txt", "UTF-8");
            i++;
        }catch (IOException ex) {
            System.out.println("Something wrong with the PrintWriter");
        }

        try {
            url = new URL(currentURL);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((lineContent = br.readLine()) != null && writer != null) {
                writer.println(lineContent);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {

            }
        }
        if(writer != null){
            writer.close();
        }
        driver.quit();


    }

    public boolean shouldProcessURL(String URL) {
        url1 = URL;
        return true;
    }
}

