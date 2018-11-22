package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PollsPage extends WebDriverTest {
    public PollsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddPoll() throws InterruptedException {
        webDriver.findElement(By.id("menu:menu-show-polls")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
        webDriver.findElement(By.id("polls:addPoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
    }

    public void editPoll(String pollName) {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Edit poll']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
    }

    public void deletePoll(String pollName) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }    
}
