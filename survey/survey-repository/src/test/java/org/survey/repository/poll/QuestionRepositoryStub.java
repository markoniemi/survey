package org.survey.repository.poll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.repository.CrudRepositoryStub;

public class QuestionRepositoryStub extends CrudRepositoryStub<Question, Long> implements QuestionRepository {

    @Override
    public List<Question> findAllByPoll(@Param("poll") Poll poll) {
        List<Question> foundQuestions = new ArrayList<Question>();
        for (Question question : entities) {
            if (question.getPoll().equals(poll)) {
                foundQuestions.add(question);
            }
        }
        return foundQuestions;
    }
}
