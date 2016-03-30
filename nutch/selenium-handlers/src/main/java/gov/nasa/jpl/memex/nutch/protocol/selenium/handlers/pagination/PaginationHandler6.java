
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


package gov.nasa.jpl.memex.nutch.protocol.selenium.handlers.Pagination;


import org.apache.nutch.protocol.interactiveselenium.InteractiveSeleniumHandler;

        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.By;
        import org.openqa.selenium.NoSuchElementException;

public class PaginationHandler6 implements InteractiveSeleniumHandler {

    public void processDriver(WebDriver driver)
    {
        //This is used to fetch the current url that the Selenium driver is crawling at the moment
        String url = driver.getCurrentUrl();

        System.out.println("Search Pagination");
        System.out.println(url);

        //checking if the current url matches the budsgunshop.com url
        if(driver.getCurrentUrl().equals("http://www.budsgunshop.com/catalog/index.php"))
        {
            //We punch in guns in the search field and press enter
            driver.findElement(By.name("keywords")).sendKeys("guns\n");

            System.out.println("handler called team 6 search pagination");

            //Error-handling is done to catch the errors thrown by findElement after evaluating
            try{

                int i = 1;

                //By xpath we check if the title equals "Next Page"
                WebElement next = driver.findElement(By.xpath("//*[@title = ' Next Page ']"));

                do{
                    if(  next != null ){
                        //if "Next Page" exists if it will click it and crawl and the new page
                        System.out.println("Pagination::nextFound " + i);
                        next.click();
                    }else{
                        break;
                    }
                    //setting the value of variable next to the "Next Page" value
                    next = driver.findElement(By.xpath("//*[@title = ' Next Page ']"));
                    ++i;

                    Thread.sleep(500);
                }while(next != null);

            }catch(NoSuchElementException e)
            //one of the most common exception found was NoSuchElementException and that is why we handled that
            {
                System.out.println("Pagination::nextNotFound.");
            }catch(InterruptedException e){
                //We print out what cause the program to interrupt, if it did.

                System.out.println("Pagination::InterruptedException. "+e.getMessage());
            }
        }

    }


    public boolean shouldProcessURL(String URL) {
        return true;
    }

}
