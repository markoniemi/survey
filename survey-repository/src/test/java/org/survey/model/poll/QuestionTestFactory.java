package org.survey.model.poll;

import java.util.ArrayList;
import java.util.List;

import org.survey.repository.EntityFactory;

public class QuestionTestFactory implements EntityFactory<Question, Long> {
private Poll poll;

    public QuestionTestFactory(Poll poll) {
        this.poll = poll;
    }

    @Override
    public List<Question> getEntities(int count) {
        List<Question> entities = new ArrayList<Question>();
        for (int i = 0; i < count; i++) {
            Question question = new Question();
            switch (i % 3) {
            case 0:
                question.setType(QuestionType.BOOLEAN);
                break;
            case 1:
                question.setType(QuestionType.TEXT);
                break;
            case 2:
                question.setType(QuestionType.LABEL);
                break;
            default:
                break;
            }
            question.setText("text" + i);
            question.setDescription("description" + i);
            question.setPoll(poll);
            entities.add(question);
        }
        return entities;
    }

    @Override
    public Question getUpdatedEntity(Question entity) {
//        Question question = null;
//        if (entity instanceof BooleanQuestion) {
//            question = new BooleanQuestion();
//        } else if (entity instanceof TextQuestion) {
//            question = new TextQuestion();
//        } else if (entity instanceof Question) {
//            question = new Question();
//        }
        entity.setType(QuestionType.BOOLEAN);
        entity.setText(entity.getText() + "_updated");
        entity.setDescription(entity.getDescription() + "_updated");
        entity.setPoll(entity.getPoll());
        return entity;
    }
}
