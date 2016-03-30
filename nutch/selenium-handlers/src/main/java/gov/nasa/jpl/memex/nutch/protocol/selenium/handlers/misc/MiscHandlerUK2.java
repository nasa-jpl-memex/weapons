
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

package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.misc;

        import org.apache.hadoop.util.StringUtils;
        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.apache.nutch.util.NutchConfiguration;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

public class MiscHandlerUK2 implements InteractiveSeleniumHandler {
    private static final Logger LOG = LoggerFactory.getLogger(Tradesnsales.class);

    public void processDriver(WebDriver driver) {
        try {
            WebElement login = driver.findElement(By.id("radio1"));
            login.click();
            login.submit();
        } catch (Exception e) {
            LOG.info(StringUtils.stringifyException(e));
        }
    }

    public boolean shouldProcessURL(String URL) {
        if(URL.contains("www.tradesnsales.com/")) {
            return true;
        }
        return false;
    }
}
