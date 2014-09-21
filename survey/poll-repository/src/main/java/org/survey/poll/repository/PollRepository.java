package org.survey.poll.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.poll.model.Poll;
import org.survey.user.model.User;

@Repository
public interface PollRepository extends CrudRepository<Poll, Long> {
    List<Poll> findAllByOwner(@Param("owner") User owner);

    Poll findByName(@Param("name") String name);
}
