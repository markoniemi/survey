package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsersPage extends WebDriverTest {
    public UsersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddUser() {
        webDriver.findElement(By.id("addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "User", webDriver.getTitle());
    }

    public void editUser(String username) throws InterruptedException {
        WebElement button = webDriver
                .findElement(By.xpath("//tr[contains(td/text(),'" + username + "')]//a[contains(@id,'edit')]"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "User", webDriver.getTitle());
    }

    public void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='" + username + "']//td[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    public void deleteUser(String username) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + username + "']//button[@id='delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Users", webDriver.getTitle());
    }
}
