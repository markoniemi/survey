package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsersPage extends AbstractPage {
    public UsersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddUser() {
        click(By.id("addUser"));
        assertTitle("User");
    }

    public void editUser(String username) {
        click(By.xpath("//tr[contains(td/text(),'" + username + "')]//a[contains(@id,'edit')]"));
        assertTitle("User");
    }

    public void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='" + username + "']//td[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    public void deleteUser(String username) {
        click(By.xpath("//tr[td='" + username + "']//button[@id='delete']"));
        assertTitle("Users");
    }
}
