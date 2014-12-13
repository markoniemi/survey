package org.survey.poll.model;

/**
 * QuestionFactory creates a Question class by using a QuestionType.
 * EditPollBean uses it when user changes a question type.
 */
public class QuestionFactory {

    /**
     * Utility class has private constructor.
     */
    private QuestionFactory() {
    }

    public static Question createQuestionFrom(Question oldQuestion,
            QuestionType type, Poll poll) {
        Question newQuestion = null;
        switch (type) {
        case LABEL:
            newQuestion = new Question();
            break;
        case TEXT:
            newQuestion = new TextQuestion();
            break;
        case BOOLEAN:
            newQuestion = new BooleanQuestion();
            break;
        default:
            newQuestion = new Question();
            break;
        }
        newQuestion.setType(oldQuestion.getType());
        newQuestion.setText(oldQuestion.getText());
        newQuestion.setPoll(poll);
        return newQuestion;
    }
}
