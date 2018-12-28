package org.survey.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.survey.model.poll.QuestionType;

public class PollPage extends AbstractPage {
    @FindBy(id = "pollName")
    private WebElement pollNameInput;
    @FindBy(id = "addQuestion")
    private WebElement addQuestionButton;
    @FindBy(id = "questionText")
    private WebElement questionTextInput;
    @FindBy(id = "savePoll")
    private WebElement savePollButton;
    @FindBy(id = "questionType")
    private WebElement questionTypeInput;

    public PollPage(WebDriver webDriver) {
        super(webDriver, "Poll");
    }

    public PollsPage addPoll(String pollName) {
        pollNameInput.sendKeys(pollName);
        savePollButton.click();
        return new PollsPage(webDriver);
    }

    public PollPage clickAddQuestion() {
        addQuestionButton.click();
        return new PollPage(webDriver);
    }

    public PollsPage addQuestion(String questionText, QuestionType questionType) {
        questionTextInput.sendKeys(questionText);
        new Select(questionTypeInput).selectByValue(questionType.name());
        savePollButton.click();
        return new PollsPage(webDriver);
    }
}
