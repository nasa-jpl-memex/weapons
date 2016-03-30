
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

import java.util.logging.Logger;

import javax.security.auth.login.Configuration;

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.codehaus.plexus.util.StringUtils;

public class LoginHandler22 implements InteractiveSeleniumHandler {
      private static final Logger LOG = LoggerFactory
                .getLogger(LoginHandler22.class);

    public void processDriver(WebDriver driver) {

        try
        {
            driver.findElement(By.tagName("body")).getAttribute("innerHTML");

            Configuration conf = NutchConfiguration.create();
            new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Find username and password fields
            List<WebElement> inputFieldsList = driver.findElements(By.tagName("input"));

            int totalSize = inputFieldsList.size();

            for (int index = 0; index < totalSize; index++)
            {
                if (inputFieldsList.get(index).getAttribute("name").equals("user"))
                {
                    js.executeScript("document.getElementByTagName('user').value = \"default_user\"");
                }

                if (inputFieldsList.get(index).getAttribute("type").equals("password"))
                {
                    js.executeScript("document.getElementByTagName('password').value = \"default_passwd\"");
                }
            }

            js.executeScript("document.getElementById(\"login-form\").submit");

            new WebDriverWait(driver, conf.getLong("libselenium.page.load.delay", 3));

        }
        catch (Exception e)
        {
            LOG.info(StringUtils.stringifyException(e));
        }

    }
    public boolean shouldProcessURL(String URL)
    {
        return true;
    }
}
