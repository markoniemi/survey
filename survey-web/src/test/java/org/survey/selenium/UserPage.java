package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.survey.model.user.Role;

public class UserPage extends WebDriverTest {
    public UserPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addUser(String username, String email, String password, Role role) throws InterruptedException {
        webDriver.findElement(By.id("addUser:editUser:username")).sendKeys(
                username);
        webDriver.findElement(By.id("addUser:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("addUser:editUser:repeatPassword")).sendKeys(
                password);
        webDriver.findElement(By.id("addUser:editUser:email")).sendKeys(email);
        selectItemInList("addUser:editUser:role", role.name());
        webDriver.findElement(By.id("addUser:addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    public void editUser(String username, String password) throws InterruptedException {
        Assert.assertEquals(username, webDriver.findElement(By.id("editUser:editUser:username")).getAttribute("value"));
        webDriver.findElement(By.id("editUser:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("editUser:editUser:repeatPassword"))
                .sendKeys(password);
        // selectItemInList("editUser:editUser:role", Role.ROLE_USER.name());
        webDriver.findElement(By.id("editUser:update")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }
    public void registerNewUser(String username, String email,
            String password) {
        webDriver.findElement(By.id("register:editUser:username")).sendKeys(
                username);
        webDriver.findElement(By.id("register:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("register:editUser:repeatPassword"))
                .sendKeys(password);
        webDriver.findElement(By.id("register:editUser:email")).sendKeys(email);
        webDriver.findElement(By.id("register:registerButton")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }
}
