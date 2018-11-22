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
        webDriver.findElement(By.id("menu-polls")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls", webDriver.getTitle());
        // TODO find a fix for click not working with maven build
        webDriver.findElement(By.id("addPoll")).click();
        // webDriver.get(serverURL + "/" + appName + "/#/polls/poll");
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Poll", webDriver.getTitle());
    }

    public void editPoll(String pollName) {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName + "']//a[@id='edit']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Assert.assertEquals(webDriver.getPageSource(), "Poll", webDriver.getTitle());
        // TODO remove when addQuestion works
        // webDriver.findElement(By.id("savePoll")).click();
        // Assert.assertEquals(webDriver.getPageSource(), "Polls",
        // webDriver.getTitle());
    }

    public void deletePoll(String pollName) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName + "']//button[@id='delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Polls", webDriver.getTitle());
    }
}
