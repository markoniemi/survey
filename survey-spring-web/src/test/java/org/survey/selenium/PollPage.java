package org.survey.selenium;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PollPage extends WebDriverTest {
    public PollPage(WebDriver webDriver) {
        super(webDriver);
    }
    public void addPoll(String pollName) throws InterruptedException {
        webDriver.findElement(By.id("pollName")).sendKeys(pollName);
        webDriver.findElement(By.id("savePoll")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Polls", webDriver.getTitle());
    }
    public void addQuestion(String questionText) {
        webDriver.findElement(By.id("addQuestion")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("questionText"))
                .sendKeys(questionText);
        webDriver.findElement(By.id("savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }
}
