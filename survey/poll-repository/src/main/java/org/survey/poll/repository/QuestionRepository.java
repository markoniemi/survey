package org.survey.poll.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.poll.model.Poll;
import org.survey.poll.model.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>{
    List<Question> findAllByPoll(@Param("poll") Poll poll);
}
