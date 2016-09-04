package org.survey.repository.poll;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.model.poll.Poll;
import org.survey.model.user.User;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
    List<Poll> findAllByOwner(@Param("owner") User owner);

    Poll findByName(@Param("name") String name);
}
