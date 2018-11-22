package org.survey.selenium;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class WebDriverTest {
    protected static final int SLEEP_TIME = 100;
    protected WebDriver webDriver;

    public WebDriverTest(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected void selectItemInList(String elementId, String value) {
        WebElement roleSelect = webDriver.findElement(By.id(elementId));
        List<WebElement> options = roleSelect.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                option.click();
            }
        }
    }

    public void logout() throws InterruptedException {
        webDriver.findElement(By.id("logout")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Login", webDriver.getTitle());
    }
}
