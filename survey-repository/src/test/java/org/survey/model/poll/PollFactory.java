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
            poll.setQuestions(new QuestionTestFactory(poll).getEntities(count));
            entities.add(poll);
        }
        return entities;
    }

    @Override
    public Poll getUpdatedEntity(Poll entity) {
        entity.setName(entity.getName() + "_updated");
        entity.setId(entity.getId());
        entity.setOwner(entity.getOwner());
        QuestionTestFactory questionTestFactory = new QuestionTestFactory(entity);
        for (Question question : entity.getQuestions()) {
            questionTestFactory.getUpdatedEntity(question);
        }
        return entity;
    }
}
