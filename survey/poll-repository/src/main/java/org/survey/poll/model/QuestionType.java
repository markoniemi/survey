package org.survey.poll.model;

public enum QuestionType {
    LABEL("LABEL"), TEXT("TEXT"), BOOLEAN("BOOLEAN");
    @SuppressWarnings("unused")
    private String type;
    private QuestionType(String type) {
        this.type = type;
    }
}
