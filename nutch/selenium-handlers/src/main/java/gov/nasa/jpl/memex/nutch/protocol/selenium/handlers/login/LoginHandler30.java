
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


        import org.apache.hadoop.conf.Configuration;
        import org.apache.nutch.protocol.interactiveselenium.DefalultMultiInteractionHandler;
        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.apache.nutch.util.NutchConfiguration;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.apache.hadoop.util.StringUtils;
        import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginHandler30 implements InteractiveSeleniumHandler {
    private static final Logger LOG = LoggerFactory
            .getLogger(DefalultMultiInteractionHandler.class);
    String currenturl="";
    public void processDriver(WebDriver driver) {

        try {
            if(currenturl.contains("http://forum.xencentral.com")||currenturl.contains("http://www.theoutdoorstrader.com")||currenturl.contains("http://xenforo.com")||currenturl.contains("http://worldwidesurvival.com"))
            {
                Configuration conf = NutchConfiguration.create();
                new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));
                if(currenturl.contains("http://xenforo.com"))
                {
                    WebElement visibleele = driver.findElement(By.xpath("//a[@href='login/']"));
                    if(visibleele!=null)
                        visibleele.click();
                    else
                        return;
                }
                WebElement passele = driver.findElement(By.xpath("//input[@type='password']"));
                WebElement userele = driver.findElement(By.xpath("//input[@name='login']"));
                WebElement submitele = driver.findElement(By.xpath("//input[@type='submit']"));
                if(passele!=null && userele!=null && submitele!=null)
                {
                    passele.sendKeys("Keys");
                    userele.sendKeys("Username");
                    submitele.click();
                }
                else
                {
                    System.out.println("fetch of "+currenturl+" failed with: Http code=403, url="+currenturl);
                }

                driver.close();
            }
            else
            {
                return;
            }

        }catch (Exception e) {
            LOG.info(StringUtils.stringifyException(e));
        }
    }

    public boolean shouldProcessURL(String URL) {

        return true;
    }
}
