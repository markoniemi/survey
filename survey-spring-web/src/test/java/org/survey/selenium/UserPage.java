package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.survey.model.user.Role;

public class UserPage extends WebDriverTest {
    public UserPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addUser(String username, String email, String password, Role role) throws InterruptedException {
        webDriver.findElement(By.id("username")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        // webDriver.findElement(By.id("repeatPassword")).sendKeys(
        // password);
        webDriver.findElement(By.id("email")).sendKeys(email);
        selectItemInList("role", role.name());
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/* webDriver.getPageSource(), */ "Users", webDriver.getTitle());
    }

    public void editUser(String username, String password) throws InterruptedException {
        WebElement passwordElement = webDriver.findElement(By.id("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        // WebElement repeatPasswordElement =
        // webDriver.findElement(By.id("repeatPassword"));
        // repeatPasswordElement.clear();
        // repeatPasswordElement.sendKeys(password);
        selectItemInList("role", Role.ROLE_USER.name());
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/* webDriver.getPageSource(), */ "Users", webDriver.getTitle());
    }
}
