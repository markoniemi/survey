package org.survey.repository.poll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.survey.model.poll.Poll;
import org.survey.model.user.User;
import org.survey.repository.CrudRepositoryStub;

public class PollRepositoryStub extends CrudRepositoryStub<Poll, Long> implements PollRepository {
    @Override
    public List<Poll> findAllByOwner(@Param("owner") User owner) {
        List<Poll> foundPolls = new ArrayList<Poll>();
        for (Poll poll : entities) {
            if (poll.getOwner().equals(owner)) {
                foundPolls.add(poll);
            }
        }
        return foundPolls;
    }

    @Override
    public Poll findByName(@Param("name") String name) {
        for (Poll poll : entities) {
            if (poll.getName().equals(name)) {
                return poll;
            }
        }
        return null;
    }
}
