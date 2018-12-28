package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.survey.model.user.Role;

public class UserPage extends AbstractPage {
    @FindBy(id = "username")
    private WebElement usernameInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "submit")
    private WebElement submitButton;
    private Select roleSelect;

    public UserPage(WebDriver webDriver) {
        super(webDriver, "User");
        roleSelect = new Select(webDriver.findElement(By.id("role")));
    }

    public UsersPage addUser(String username, String email, String password, Role role) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        emailInput.sendKeys(email);
        roleSelect.selectByValue(role.name());
        submitButton.click();
        return new UsersPage(webDriver);
    }

    public UsersPage editUser(String username, String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        roleSelect.selectByValue(Role.ROLE_USER.name());
        submitButton.click();
        return new UsersPage(webDriver);
    }
}
