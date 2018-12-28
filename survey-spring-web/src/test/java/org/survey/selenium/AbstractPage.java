package org.survey.selenium;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {
    protected static final int SLEEP_TIME = 100;
    protected WebDriver webDriver;

    public AbstractPage(WebDriver webDriver, String title) {
        this.webDriver = webDriver;
        assertTitle(title);
        PageFactory.initElements(webDriver, this);
    }

    protected void selectItemInList(String elementId, String value) {
        WebElement select = webDriver.findElement(By.id(elementId));
        List<WebElement> options = select.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                option.click();
            }
        }
    }

    public LoginPage logout() {
        webDriver.findElement(By.id("logout")).click();
        return new LoginPage(webDriver);
    }

    public PollsPage polls() {
        webDriver.findElement(By.id("menu-polls")).click();
        return new PollsPage(webDriver);
    }

    protected void setText(By by, String username) {
        webDriver.findElement(by).clear();
        webDriver.findElement(by).sendKeys(username);
    }

    protected void click(By by) {
        webDriver.findElement(by).click();
    }

    protected void assertTitle(String title) {
        Assert.assertEquals(webDriver.getPageSource(), title, webDriver.getTitle());
    }
}
