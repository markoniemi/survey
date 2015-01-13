package org.survey.model.poll;

import java.util.ArrayList;
import java.util.List;

import org.survey.model.poll.BooleanQuestion;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.poll.TextQuestion;
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
            Question question = null;
            switch (i % 3) {
            case 0:
                question = new BooleanQuestion();
                break;
            case 1:
                question = new TextQuestion();
                break;

            case 2:
                question = new Question();
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
        Question question = null;
        if (entity instanceof BooleanQuestion) {
            question = new BooleanQuestion();
        } else if (entity instanceof TextQuestion) {
            question = new TextQuestion();
        } else if (entity instanceof Question) {
            question = new Question();
        }
        question.setType(entity.getType());
        question.setText(entity.getText() + "_updated");
        question.setDescription(entity.getDescription() + "_updated");
        question.setPoll(entity.getPoll());
        return question;
    }
}
