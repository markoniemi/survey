package org.survey.repository.poll;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>{
    List<Question> findAllByPoll(@Param("poll") Poll poll);
}
