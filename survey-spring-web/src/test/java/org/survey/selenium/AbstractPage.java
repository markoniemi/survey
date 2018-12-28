package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public abstract class AbstractPage {
    protected static final int SLEEP_TIME = 100;
    protected WebDriver webDriver;

    public AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void logout() {
        click(By.id("logout"));
        assertTitle("Login");
    }

    public void polls() {
        click(By.id("menu-polls"));
        assertTitle("Polls");
    }

    protected void setText(By by, String value) {
        webDriver.findElement(by).clear();
        webDriver.findElement(by).sendKeys(value);
    }

    protected void click(By by) {
        webDriver.findElement(by).click();
    }

    protected void assertTitle(String title) {
        Assert.assertEquals(webDriver.getPageSource(), title, webDriver.getTitle());
    }

    protected void selectByValue(By by, String value) {
        new Select(webDriver.findElement(by)).selectByValue(value);
    }
}
