package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsersPage extends WebDriverTest {
    public UsersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddUser() {
        webDriver.findElement(By.id("users:addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add user",
                webDriver.getTitle());
    }

    public void editUser(String username) throws InterruptedException {
        WebElement button = webDriver.findElement(By
                .xpath("//tr[contains(td/text(),'" + username
                        + "')]//input[contains(@id,'edit')]"));
//        WebElement button = browser.findElement(By
//                .xpath("//input/@title,'" + username + "')]"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Edit user",
                webDriver.getTitle());
    }

    public void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='"
                + username + "']//span[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }
    public void assertNoDeleteOrAdd() {
        try {
            webDriver.findElement(By.xpath("//tr//input[@value='Delete']"));
            Assert.fail();
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    public void deleteUser(String username) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + username
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }
}
