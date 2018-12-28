package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.survey.model.user.Role;

public class UserPage extends AbstractPage {
    public UserPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addUser(String username, String email, String password, Role role) {
        setText(By.id("username"), username);
        setText(By.id("password"), password);
        setText(By.id("email"), email);
        selectByValue(By.id("role"), role.name());
        click(By.id("submit"));
        assertTitle("Users");
    }

    public void editUser(String username, String password) {
        setText(By.id("password"), password);
        selectByValue(By.id("role"), Role.ROLE_USER.name());
        click(By.id("submit"));
        assertTitle("Users");
    }
}
