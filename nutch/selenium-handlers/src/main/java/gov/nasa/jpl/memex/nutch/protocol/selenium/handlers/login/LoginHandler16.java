package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;


        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.By;
        import org.openqa.selenium.ElementNotVisibleException;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginHandler16 implements InteractiveSeleniumHandler {

    @Override
    public void processDriver(WebDriver driver) {
        System.out.println("=== INFO: DonglinHandler processing...");
        String url = truncateUrl(driver.getCurrentUrl());

        switch (url) {
            case "arguntrader.com":
                fetchArguntrader(driver);
                break;
            case "cheaperthandirt.com":
                fetchCheaperthandirt(driver);
                break;
            case "hawaiiguntrader.com":
                fetchHawaiiguntrader(driver);
                break;
            case "kyclassifieds.com":
                fetchKyclassifieds(driver);
                break;
            case "ksl.com":
                fetchKslCom(driver);
                break;
            case "iwanna.com":
                fetchIwanna(driver);
                break;
            case "msguntrader.com":
                fetchMsguntrader(driver);
                break;
            // http://www.donglinpu.me/csci572 is for testing purpose.
            // run bin/nutch parsechecker http://www.donglinpu.me/csci572
            // Expected to see a PDF and 2 images urls.
//		case "donglinpu.me":
//			fetchDonglinPuMe(driver);
//			break;

            // Add case here.
            default:
                break;
        }

        driver.close();
    }


    /**
     * @author Donglin Pu
     * @param URL: input URL. We need to decide if we need to run interaction on this url.
     * 			If so, return true. Then processDriver function will run.
     */
    @Override
    public boolean shouldProcessURL(String URL) {
        URL = truncateUrl(URL);
        switch (URL) {
            case "arguntrader.com":
            case "cheaperthandirt.com":
            case "hawaiiguntrader.com":
            case "kyclassifieds.com":
            case "ksl.com":
            case "msguntrader.com":

            case "freegunclassifieds.com":
            case "iwanna.com":
//		case "donglinpu.me":
                // More cases add here.
                return true;
            default:
                return false;
        }
    }


    /**
     * @author Donglin Pu
     * @param url: the URL to be fetched.
     * @return truncated url.
     * Examples:
     * 	www.example.com => example.com
     * 	http://example.com => example.com
     * 	http://www.example.com => example.com
     */
    private String truncateUrl (String url) {
        if (url.startsWith("http://")) {
            url = url.substring(7);
            url = url.split("/")[0];
        } else if (url.startsWith("https://")) {
            url = url.substring(8);
            url = url.split("/")[0];
        }
        if (url.startsWith("www.")) {
            url = url.substring(4);
            url = url.split("/")[0];
        }
        return url;
    }


    /**
     * @author DongminatorDonglin Pu
     * This method is for testing only.
     */
    private void fetchDonglinPuMe (WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.id("clickThis"));
        if (elements.size() != 0) {
            elements.get(0).click();
        }
    }


    /**
     * @author Donglin Pu
     * Fetch http://www.kyclassifieds.com/
     * Username: Oct042015
     * Password: HIDDED
     */
    private void fetchKyclassifieds (WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.id("login-form"));
        if (elements.size() != 0) {
            WebElement loginForm = elements.get(0);
            if (loginForm != null) {
                WebElement loginUsername = driver.findElement(By.id("login_username"));
                WebElement loginPassword = driver.findElement(By.id("login_password"));
                WebElement loginButton = driver.findElement(By.id("login"));
                loginUsername.sendKeys("Oct042015");
                loginPassword.sendKeys("HIDDED");
                loginButton.click();
            }
        }
    }


    /**
     * @author Donglin
     * Fetch www.ksl.com
     * Username: csci572.20151004@gmail.com
     * Password: HIDDED
     */
    private void fetchKslCom (WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[@action='/public/member/signin']"));
        if (elements.size() == 0) {
            elements = driver.findElements(By.xpath("//*[@action='/public/member/login']"));
        }
        if (elements.size() != 0) {
            WebElement loginForm = elements.get(0);
            if (loginForm != null) {
                WebElement loginUsername = driver.findElement(By.id("memberemail"));
                WebElement loginPassword = driver.findElement(By.id("memberpassword"));
                WebElement loginButton = driver.findElement(By.xpath("//*[@value='Log In']"));
                loginUsername.sendKeys("csci572.20151004@gmail.com");
                loginPassword.sendKeys("HIDDED");
                loginButton.click();
            }
        }
    }


    /**
     * @author Donglin
     * Fetch www.hawaiiguntrader.com
     */
    private void fetchHawaiiguntrader (WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.id("login-form"));
        if (elements.size() != 0) {
            WebElement loginForm = elements.get(0);
            if (loginForm != null) {
                driver.findElement(By.id("login_username")).sendKeys("csci572.20151004@gmail.com");
                driver.findElement(By.id("login_password")).sendKeys("HIDDED");
                driver.findElement(By.id("login")).click();
            }
        }
    }


    /**
     * @author Donglin
     * Fetch www.arguntrader.com
     * Example http://www.arguntrader.com/viewforum.php?f=36&sid=fb1773c91934a3e1a9411e73d6276401
     * Oct042015:HIDDED
     * Find by <form action="./ucp.php?mode=login"...
     */
    private void fetchArguntrader (WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.xpath("//input[@type='submit' and @name='login']")); // find the <input> login button
        if (elements.size() != 0) {
            WebElement loginUsername = driver.findElement(By.id("username"));
            WebElement loginPassword = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.xpath("//*[@value='Login']"));
            loginUsername.sendKeys("Oct042015");
            loginPassword.sendKeys("HIDDED");
            loginButton.click();

//			WebDriverWait wdw = new WebDriverWait(driver, 10);
//			WebElement myDynamicElement = wdw.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Return to the previous page')]")));
//			System.out.println("=== Waited === ");
        }
    }


    /**
     * @author Donglin Pu
     * Fetch www.cheaperthandirt.com
     * Login: Oct042015 : KTrZ!MM^C#I1
     * Wait for popup (sumome-popup-form): If see popup: click close popup: $(".sumome-popup-close").click()
     */
    private void fetchCheaperthandirt (WebDriver driver){
        System.out.println("== Fetching cheaperhandirt ==");
        try {
            WebDriverWait wdw = new WebDriverWait(driver, 10);
            WebElement popup = wdw.until(ExpectedConditions.presenceOfElementLocated(By.className("sumome-popup-form")));
            if (popup != null) {
                System.out.println("== here is popup! ==");
                WebElement popupCloseDivByXPATH = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[3]"));
                popupCloseDivByXPATH.click();
            }
        } catch (NoSuchElementException ex) {
            System.out.println("=== INFO: NoSuchElementException caught: popup element not found");
            driver.navigate().refresh();
        } catch (ElementNotVisibleException ex) { // this one worked!
            System.out.println("=== INFO: ElementNotVisibleException caught: popup element not found");
            driver.navigate().refresh();
        } catch (Exception e) {
            System.out.println("=== INFO: Some Exception caught: popup element not found");
        }

    }


    /**
     * @author Donglin Pu
     * Fetch: http://msguntrader.com/
     * Credential: WtfIsWangGuard : WtfIsWangGuard
     */
    private void fetchMsguntrader (WebDriver driver) {
        List<WebElement> loginFormList = driver.findElements(By.id("login-form"));
        if (loginFormList.size() > 0) { // form exist
            try {

                WebElement loginInput = driver.findElement(By.id("login_username"));
                WebElement passwordInput = driver.findElement(By.id("login_password"));
                WebElement loginButton = driver.findElement(By.id("login"));

                loginInput.sendKeys("WtfIsWangGuard");
                passwordInput.sendKeys("WtfIsWangGuard");
                loginButton.click();

            } catch (Exception e) {
                // do nothing
            }
        }
    }



    /**
     * @author Donglin Pu
     * Fetch: http://www.iwanna.com/
     * This one has Javascript pagination
     * Example: http://www.iwanna.com/marketplace/forsale?ordby=&ordtype=&page=2&rows=30&adtype=FS&order_list=
     */
    public void fetchIwanna (WebDriver driver){
        // Find pagination by id = paging
        List<WebElement> paging = driver.findElements(By.id("paging"));
        if (paging.size() > 0) { // form exist
            try {
                String url = driver.getCurrentUrl();

                String[] checkParams = url.split("\\?");
                int currPage = 1;

                if ( checkParams.length > 1) {
                    String[] params = checkParams[1].split("&");
                    System.out.println(Arrays.toString(params));
                    Map<String, String> map = new HashMap<String, String>();
                    for (String param : params)
                    {
                        String name = param.split("=")[0];
                        String value = param.split("=")[1];
                        if (name == "page") {
                            map.put(name, value);
                        }
                    }

                    if (map.size() > 0) {
                        currPage = Integer.parseInt(map.get("page"));
                        System.out.println("=== Got page: " + currPage);
                    } else {
                        System.out.println("=== No page parameter in URL");
                    }
                } else {
                    // We are on page 1.
                    currPage = 1;
                }

                // Find the active element
                WebElement activeA = driver.findElement(By.xpath("//div[@id='paging']/div[@class='listing_paging']/span[@class='paging_link']/a[@class='active']"));
                // Find the element next to the active element.
                WebElement nextPageClickElement = driver.findElement(By.xpath("//div[@id='paging']/div[@class='listing_paging']/span[@class='paging_link']/a[@class='active']/following-sibling::a[1]"));

                System.out.println("=== Active page: " + activeA.getText() + " || To click on page: " + nextPageClickElement.getText());

                // Click on the page number found on the nextPageClickElement
                nextPageClickElement.click();

            } catch (Exception e) {
                System.out.println("=== there is an exeption. ===");
                System.out.println(e.getMessage());
                System.out.println("=== End of erro message. ===");
                // do nothing
            }
        } else {
            System.out.println("=== INFO: ID=paging not found. ");
        }
    }


}
