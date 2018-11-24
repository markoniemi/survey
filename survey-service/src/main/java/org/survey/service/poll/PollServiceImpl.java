package org.survey.service.poll;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.user.User;
import org.survey.repository.poll.PollRepository;
import org.survey.repository.user.UserRepository;

import com.google.common.collect.Iterables;

/**
 * Implementation of PollService. endpointInterface and serviceName are probably
 * unneccessary.
 */
@WebService(endpointInterface = "org.survey.service.poll.PollService", serviceName = "pollService")
public class PollServiceImpl implements PollService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PollRepository pollRepository;
    private static final Poll[] EMPTY_POLL_ARRAY = new Poll[0];

    @Override
    public Poll[] findAll() {
        return Iterables.toArray(pollRepository.findAll(), Poll.class);
    }

    public Poll[] findByOwner(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return EMPTY_POLL_ARRAY;
        }
        return Iterables.toArray(pollRepository.findAllByOwner(user), Poll.class);
    }

    @Override
    public Poll create(Poll poll) {
        return update(poll);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Poll update(Poll poll) {
        if (CollectionUtils.isNotEmpty(poll.getQuestions())) {
            for (Question question : poll.getQuestions()) {
                question.setPoll(poll);
            }
        }
        return pollRepository.save(poll);
    }

    @Override
    public Poll findOne(Long id) {
        return pollRepository.findOne(id);
    }

    @Override
    public Poll findByName(String name) {
        return pollRepository.findByName(name);
    }

    @Override
    public boolean exists(Long id) {
        return pollRepository.findOne(id) != null;
    }

    @Override
    public void delete(Long id) {
        Poll poll = pollRepository.findOne(id);
        if (poll == null) {
            return;
        }
        poll.getQuestions().clear();
        pollRepository.save(poll);
        pollRepository.delete(poll.getId());
    }

    @Override
    public long count() {
        return pollRepository.count();
    }
}
