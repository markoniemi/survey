package org.survey.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    @FindBy(id = "j_username")
    private WebElement usernameInput;
    @FindBy(id = "j_password")
    private WebElement passwordInput;
    @FindBy(id = "loginButton")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver) {
        super(webDriver, "Login");
    }

    public UsersPage login(String username, String password) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        return new UsersPage(webDriver);
    }
}
