package org.apache.nutch.protocol.interactiveselenium.handlers;


import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Search handler to find search fields and input search keys **/

public class SearchHandler implements InteractiveSeleniumHandler {

    private String searchWindowPathFromUrl(String url) {
        switch (url) {
            case "http://www.ksl.com/" : return "//*[@id='kslHeader']/div/div[1]/div/div[4]/a/span";
            case "http://www.kyclassifieds.com/" : return "//*[@id='s']";
            case "http://www.gowilkes.com/": return "//*[@id='search_box']";
            case "http://www.nextechclassifieds.com/" : return "//*[@id='nav-search-input']";
            default: return "";
        }
    }

    private String searchInputPathFromUrl(String url) {
        switch (url) {
            case "http://www.ksl.com/" : return "ksl-header-search__input";
            case "http://www.wantaddigest.com/": return "//*[@id='country']";
            default: return "";
        }
    }

    private String searchButtonPathFromUrl(String url) {
        switch (url) {
            case "http://www.ksl.com/" : return "//*[@id='kslHeader']/div/div[1]/div/div[4]/div/form/button";
            case "http://www.kyclassifieds.com/" : return "//*[@id='go']";
            case "http://www.gowilkes.com/" : return "//*[@id='search_container']/form/table/tbody/tr/td[2]/span";
            case "http://www.wantaddigest.com/" : return "//*[@id='left-home']/form/input[2]";
            case "" : return "//*[@id='search-btn']";
            default: return "";
        }
    }

    private String searchRadioButtonPath(String url) {
        switch (url) {
            case "http://www.gowilkes.com/" : return "//*[@id='search_container']/form/div/div[5]/input";
            default: return "";
        }
    }

    private String searchKeyFromUrl(String url) {
        switch (url) {
            default: return "guns";
        }
    }

    private String genericSearchHandler(WebDriver webDriver, String htmlpage, String searchWindowPath, String searchInputPath, String searchButtonPath, String searchRadioButtonPath, String searchKey) {
        webDriver.manage().window().maximize();
        WebElement searchWindow = webDriver.findElement(By.xpath(searchWindowPath));
        searchWindow.click();
        WebElement username = webDriver.findElement(By.className(searchInputPath));
        username.sendKeys(searchKey);
        WebElement classifiedRadioBtn = webDriver.findElement(By.xpath(searchRadioButtonPath));
        classifiedRadioBtn.click();
        WebElement loginBtn = webDriver.findElement(By.xpath(searchButtonPath));
        loginBtn.click();
        htmlpage+="</body></html>";
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("document.body.innerHTML=arguments[0]", htmlpage);
        return htmlpage;
    }

    public String processDriver (WebDriver webDriver){
        String htmlpage="<html><body>";
        String currentUrl = webDriver.getCurrentUrl();
        String searchWindowPath = searchWindowPathFromUrl(currentUrl);
        String searchInputPath = searchInputPathFromUrl(currentUrl);
        String searchButtonPath = searchButtonPathFromUrl(currentUrl);
        String searchRadioButton = searchRadioButtonPath(currentUrl);
        String searchKey = searchKeyFromUrl(currentUrl);
        htmlpage += genericSearchHandler(webDriver, htmlpage, searchWindowPath, searchInputPath, searchButtonPath, searchRadioButton, searchKey);
        return htmlpage;
    }
    public boolean shouldProcessURL(String URL) {
        System.out.println("URL IS "+ URL);
        return true;
    }
}
