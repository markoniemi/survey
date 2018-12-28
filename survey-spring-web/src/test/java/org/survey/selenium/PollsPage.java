package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PollsPage extends AbstractPage {
    @FindBy(id = "addPoll")
    private WebElement addPollButton;

    public PollsPage(WebDriver webDriver) {
        super(webDriver, "Polls");
    }

    public PollPage clickAddPoll() {
        addPollButton.click();
        return new PollPage(webDriver);
    }

    public PollPage editPoll(String pollName) {
        webDriver.findElement(By.xpath("//tr[td='" + pollName + "']//a[@id='edit']")).click();
        return new PollPage(webDriver);
    }

    public PollsPage deletePoll(String pollName) {
        webDriver.findElement(By.xpath("//tr[td='" + pollName + "']//button[@id='delete']")).click();
        return new PollsPage(webDriver);
    }
}
