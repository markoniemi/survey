package org.survey.poll.service;

import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.survey.poll.model.Poll;
import org.survey.poll.model.Question;
import org.survey.poll.repository.PollRepository;
import org.survey.poll.repository.QuestionRepository;
import org.survey.user.model.User;
import org.survey.user.repository.UserRepository;

import com.google.common.collect.Iterables;

/**
 * Implementation of PollService. endpointInterface and serviceName are probably
 * unneccessary.
 */
@Transactional
@WebService(endpointInterface = "org.survey.poll.service.PollService", serviceName = "pollService")
public class PollServiceImpl implements PollService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private static final Poll[] EMPTY_POLL_ARRAY = new Poll[0];

    @Override
    public Poll[] findAll() {
        Iterable<Poll> polls = pollRepository.findAll();
        // return empty list instead of null
        if (Iterables.isEmpty(polls)) {
            return EMPTY_POLL_ARRAY;
        } else {
            return Iterables.toArray(polls, Poll.class);
        }
    }

    public Poll[] findByOwner(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return EMPTY_POLL_ARRAY;
        }
        Iterable<Poll> polls = pollRepository.findAllByOwner(user);
        // return empty list instead of null
        if (Iterables.isEmpty(polls)) {
            return EMPTY_POLL_ARRAY;
        } else {
            return Iterables.toArray(polls, Poll.class);
        }
    }

    @Override
    public Poll create(Poll poll) {
        Validate.notNull(poll);
        Validate.isTrue(!exists(poll.getName()), "Poll already exists: {}", poll.getName());
        return update(poll);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Poll update(Poll poll) {
        Poll savedPoll = pollRepository.save(poll);
        Iterable<Question> savedQuestions = questionRepository.save(poll.getQuestions());
        poll.setQuestions(IteratorUtils.toList(savedQuestions.iterator()));
        return savedPoll;
    }

    @Override
    // TODO change to id
    public Poll findOne(String username) {
        return pollRepository.findByName(username);
    }

    @Override
    public boolean exists(String username) {
        return pollRepository.findByName(username) != null;
    }

    @Override
    public void delete(String name) {
        Poll poll = pollRepository.findByName(name);
        if (poll == null) {
            return;
        }
        List<Question> questions = questionRepository.findAllByPoll(poll);
        questionRepository.delete(questions);
        pollRepository.delete(poll.getId());
    }

    @Override
    public long count() {
        return pollRepository.count();
    }
}
