package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends AbstractPage {
    @FindBy(id = "addUser")
    private WebElement addUserButton;

    public UsersPage(WebDriver webDriver) {
        super(webDriver, "Users");
    }

    public UserPage clickAddUser() {
        webDriver.findElement(By.id("addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "User", webDriver.getTitle());
        return new UserPage(webDriver);
    }

    public UserPage editUser(String username) {
        WebElement button = webDriver
                .findElement(By.xpath("//tr[contains(td/text(),'" + username + "')]//a[contains(@id,'edit')]"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        return new UserPage(webDriver);
    }

    public void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='" + username + "']//td[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    public UsersPage deleteUser(String username) {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + username + "']//button[@id='delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        return new UsersPage(webDriver);
    }
}
