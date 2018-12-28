package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void login(String username, String password) {
        setText(By.id("j_username"), username);
        setText(By.id("j_password"), password);
        click(By.id("loginButton"));
        assertTitle("Users");
    }
}
