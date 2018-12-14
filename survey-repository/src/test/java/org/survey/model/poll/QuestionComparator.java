package org.survey.model.poll;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class QuestionComparator implements Comparator<Question> {

    @Override
    public int compare(Question question1, Question question2) {
        if (question1 == question2) {
            return 0;
        }
        if (question1 == null) {
            return -1;
        }
        if (question2 == null) {
            return 1;
        }
        return new CompareToBuilder().append(question1.getType(), question2.getType())
                .append(question1.getText(), question2.getText())
                .append(question1.getDescription(), question2.getDescription()).toComparison();
    }
}
