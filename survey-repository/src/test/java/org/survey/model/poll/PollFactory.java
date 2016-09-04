package org.survey.model.poll;

import java.util.ArrayList;
import java.util.List;

import org.survey.model.user.User;
import org.survey.repository.EntityFactory;

public class PollFactory implements EntityFactory<Poll, Long> {
    private User user;

    public PollFactory(User user) {
        this.user = user;
    }

    @Override
    public List<Poll> getEntities(int count) {
        List<Poll> entities = new ArrayList<Poll>();
        for (int i = 0; i < count; i++) {
            Poll poll = new Poll("poll name " + i);
            poll.setOwner(user);
            poll.setQuestions(new ArrayList<Question>());
            for (int j = 0; j < count; j++) {
                Question question = new Question();
                question.setType(QuestionType.LABEL);
                question.setText("text");
                question.setPoll(poll);
                poll.getQuestions().add(question);
            }
            entities.add(poll);
        }
        return entities;
    }

    @Override
    public Poll getUpdatedEntity(Poll entity) {
        // cannot update name, since it is the the natural id
//        Poll poll = new Poll(entity.getName());
        entity.setOwner(entity.getOwner());
        for (Question question : entity.getQuestions()) {
            question.setType(QuestionType.BOOLEAN);
            question.setText(question.getText() + "_updated");
        }
//        entity.setQuestions(entity.getQuestions());
        return entity;
    }
}
