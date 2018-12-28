package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PollsPage extends AbstractPage {
    public PollsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickAddPoll() {
        click(By.id("addPoll"));
        assertTitle("Poll");
    }

    public void editPoll(String pollName) {
        click(By.xpath("//tr[td='" + pollName + "']//a[@id='edit']"));
        assertTitle("Poll");
    }

    public void deletePoll(String pollName) {
        click(By.xpath("//tr[td='" + pollName + "']//button[@id='delete']"));
        assertTitle("Polls");
    }
}
