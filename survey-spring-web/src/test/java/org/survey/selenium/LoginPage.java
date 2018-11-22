package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends WebDriverTest {
    
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open(String loginUrl) throws InterruptedException {
        webDriver.get(loginUrl);
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Login", webDriver.getTitle());
    }

    public void login(String username, String password) throws InterruptedException {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys(username);
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys(password);
        webDriver.findElement(By.id("loginButton")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Users", webDriver.getTitle());
    }
}
