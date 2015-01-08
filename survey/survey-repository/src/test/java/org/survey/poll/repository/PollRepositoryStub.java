package org.survey.poll.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.survey.poll.model.Poll;
import org.survey.repository.CrudRepositoryStub;
import org.survey.user.model.User;

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
