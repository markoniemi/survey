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

    public void login(String username, String password) {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys(username);
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys(password);
        webDriver.findElement(By.id("loginButton")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }
    public void loginError() {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys("");
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys("");
        webDriver.findElement(By.id("loginButton")).click();
        Assert.assertEquals("Login", webDriver.getTitle());
        Assert.assertTrue(webDriver.getPageSource().contains("Login error"));
    }
    public void clickRegisterNewUser() {
        webDriver.findElement(By.id("register:register")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Register",
                webDriver.getTitle());
    }

    public void logout() {
        webDriver.findElement(By.id("menu:logout")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }
}
