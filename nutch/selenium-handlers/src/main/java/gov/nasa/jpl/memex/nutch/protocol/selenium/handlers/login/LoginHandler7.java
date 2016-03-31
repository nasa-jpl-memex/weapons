package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.login;

// Selenium Login Handler extends protocol-interactive-selenium interface

        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginHandler7 implements InteractiveSeleniumHandler {
    public static WebDriver driver;
    public static void main(String args[]) {
        BufferedReader br = null;
        try {
            driver = new FirefoxDriver();
            // Reading seed urls from the file
            br = new BufferedReader(
                    new FileReader(
                            "/Users/shivangibansal/Desktop/SeleniumMaven/src/main/java/org/apache/nutch/protocol/interactiveselenium/seeds.txt"));
            String line = br.readLine();
            while (line != null) {
                // Visit the web page
                driver.get(line);
                new LoginHandler().processDriver(driver);
                line = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
            }
        }

    }// End of main()

    public void processDriver(WebDriver driver) {
        CharSequence[] email = { "//Email" };
        CharSequence[] teamName = { "TeamName" };
        CharSequence[] passwd = { "Password" };
        // Specifying the common keywords(ids, names or classes) used for login and password fields
        String[] loginValues = { "username", "Nickname or Email Address",
                "login", "User name", "Login", "Username", "User Name",
                "user name" };
        List loginList = new ArrayList(Arrays.asList(loginValues));
        String[] buttonValues = { "SIGN IN", "Sign In", "Submit", "Login",
                "SignIn", "submit", "log in", "sign in", "Log in", "Sign in",
                "Log In", "login", "I Agree"};
        List buttonList = new ArrayList(Arrays.asList(buttonValues));
        // Finding the link to Login page from the home page of the seed url
        WebElement initialElement = null;
        for (int j = 0; j < buttonList.size(); j++) {
            initialElement = initialElement != null ? initialElement
                    : (getWebDriver(driver, (String) buttonList.get(j)));
        }
        // Clicking the link to the login page if it exists
        if (initialElement != null)
            initialElement.click();
        else
            ;

        // Getting all the possible ids for the email field using Xpath
        List list = driver.findElements(By
                .xpath("//input[contains(@id, 'Email')]"));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'email')]")));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'EMail')]")));
        list = driver.findElements(By
                .xpath("//input[contains(@value, 'Email')]"));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@value, 'email')]")));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@value, 'EMail')]")));

        // Filling Email value in the text field
        fillElements(email, list);


        // start filling username
        for (int i = 0; i < loginList.size(); i++) {
            list.addAll(driver.findElements(By.xpath("//input[contains(@id, '"
                    + loginList.get(i) + "')]")));
            list.addAll(driver.findElements(By.xpath("//input[contains(@value, '"
                    + loginList.get(i) + "')]")));
        }
        fillElements(teamName, list);
        // End filling username

        // Getting all the possible ids for the password field using Xpath
        list = driver.findElements(By
                .xpath("//input[contains(@id, 'Password')]"));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'password')]")));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'Password1')]")));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'passwd')]")));
        list.addAll(driver.findElements(By
                .xpath("//input[contains(@id, 'pass')]")));

        fillElements(passwd, list);

        // End password

        for (int j = 0; j < buttonList.size(); j++) {
            list.addAll(driver.findElements(By
                    .xpath("//button[contains(text(),'" + buttonList.get(j)
                            + "')]")));
            list.addAll(driver.findElements(By
                    .xpath("//button[contains(@value,'" + buttonList.get(j)
                            + "')]")));
            list.addAll(driver.findElements(By
                    .xpath("//input[contains(text(),'" + buttonList.get(j)
                            + "')]")));
            list.addAll(driver.findElements(By
                    .xpath("//input[contains(@value,'" + buttonList.get(j)
                            + "')]")));
        }

        if (list.isEmpty() || list.size() == 0) {
            // Accessing Buttons
            List teList = driver.findElements(By
                    .xpath("//button[contains(text(),'')]"));

            //Loop to check if any button is related to login
            for (int i = 0; i < teList.size(); i++) {
                WebElement ele = ((WebElement) teList.get(i));
                System.out.println("::Text " + ele.getText());

                //Checking for all login values
                for (int j = 0; j < buttonList.size(); j++) {
                    //If button text relates to login add it is clickable list
                    if (ele.getText().contains(buttonList.get(j).toString())) {
                        list.add(ele);
                        System.out.println("::Text " + ele.getText());

                    }
                }
            }

        }
        // Clicking the elements
        for (int i = 0; i < list.size(); i++) {
            if (((WebElement) list.get(i)).isDisplayed())
                ((WebElement) list.get(i)).click();
        }

    }

    // Method for calling filling data method over the list
    private void fillElements(CharSequence[] email, List list) {
        for (int i = 0; i < list.size(); i++) {
            fillData((WebElement) list.get(i), email);
        }
        list.clear();
    }

    // Method for filling data into the text fields
    private void fillData(WebElement element, CharSequence[] text) {
        if (element != null && element.isDisplayed()) {
            element.click();
            element.clear();
            element.sendKeys(text);
        }
    }

    //@override
    private WebElement getWebDriver(WebDriver driver, String id) {
        WebElement element = null;
        System.out.println("Enter");
        try {
            // Finding element by text in the link tag
            element = driver.findElement(By.linkText(id));
            // Finding element by name
            element = element != null ? element : driver.findElement(By
                    .name(id));
            // Finding element by id
            element = element != null ? element : driver.findElement(By.id(id));
            // Finding element by text in the link tag
            element = element != null ? element : driver.findElement(By
                    .partialLinkText(id));
        } catch (Exception e) {
            System.err.println("Id not found " + id);
        }
        return element;
    }

    // Method to check urls to be processed
    public boolean shouldProcessURL(String URL) {
        return true;
    }
}


