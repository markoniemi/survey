package org.survey.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.survey.model.poll.QuestionType;

public class PollPage extends AbstractPage {
    public PollPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addPoll(String pollName) {
        setText(By.id("pollName"), pollName);
        click(By.id("savePoll"));
        assertTitle("Polls");
    }

    public void clickAddQuestion() {
        click(By.id("addQuestion"));
        assertTitle("Poll");
    }

    public void addQuestion(String questionText, QuestionType questionType) {
        setText(By.id("questionText"), questionText);
        selectByValue(By.id("questionType"), questionType.name());
        click(By.id("savePoll"));
        assertTitle("Polls");
    }
}
