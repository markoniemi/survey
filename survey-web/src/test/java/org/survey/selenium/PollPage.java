package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PollPage extends WebDriverTest {
    public PollPage(WebDriver webDriver) {
        super(webDriver);
    }
    public void addPoll(String pollName) throws InterruptedException {
        webDriver.findElement(By.id("addPoll:pollName")).sendKeys(pollName);
        webDriver.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }
    public void addQuestion(String questionText) {
        webDriver.findElement(By.id("addPoll:addQuestion")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("addPoll:questions:0:questionText"))
                .sendKeys(questionText);
        webDriver.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }
}
