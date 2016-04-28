package org.apache.nutch.protocol.interactiveselenium.handlers;

import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ScrollHandler implements InteractiveSeleniumHandler {

    public void handlefloridagunclassifieds(WebDriver webDriver) {
        for(int i=0;i<20;i++) {
            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            jse.executeScript("window.scrollBy(0,250)");
        }
    }

    public String processDriver (WebDriver webDriver) {
        String htmlpage = "";
        String currentUrl = webDriver.getCurrentUrl();
        switch (currentUrl) {
            case "http://floridagunclassifieds.com/": handlefloridagunclassifieds(webDriver);
                break;
            default:
                throw new IllegalArgumentException("No definition for "+currentUrl);
        }

        try {
            Thread.sleep(10000);
            htmlpage += webDriver.findElement(By.tagName("body")).getAttribute("innerHTML");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return htmlpage;
    }
    public boolean shouldProcessURL(String URL) {
        System.out.println("URL IS "+ URL);
        return true;
    }
}