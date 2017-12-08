package org.survey.repository.poll;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.RepositoryStubTestConfig;
import org.survey.model.poll.Poll;
import org.survey.model.poll.PollComparator;
import org.survey.model.poll.PollFactory;
import org.survey.model.user.User;
import org.survey.model.user.UserFactory;
import org.survey.repository.CrudRepositoryTest;
import org.survey.repository.user.UserRepository;

/**
 * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryStubTestConfig.class))
public class PollRepositoryTest extends CrudRepositoryTest<Poll, Long> {
	@Resource
	protected PollRepository pollRepository;
    @Resource
    protected UserRepository userRepository;
    private User user;

	public PollRepository getEntityRepository() {
		return pollRepository;
	}

	@Before
	public void setUp() {
        UserFactory userFactory = new UserFactory();
        user = userFactory.getEntities(1).get(0);
        user = userRepository.save(user);
		entityFactory = new PollFactory(user);
		entityComparator = new PollComparator();
	}
	
    @After
    public void tearDown() {
        pollRepository.deleteAll();
        userRepository.deleteAll();
    }

	@Test
	public void findByName() {
	    save();
	    Poll originalPoll = orginalEntities.get(0);
	    Poll poll = (pollRepository.findByName(
	            originalPoll.getName()));
	    Assert.assertEquals(0, entityComparator.compare(originalPoll, poll));
	}
	
	@Test
	public void findAllByOwner() {
	    save();
	    Poll originalPoll = orginalEntities.get(0);
	    @SuppressWarnings("unchecked")
	    List<Poll> polls = IteratorUtils.toList(pollRepository.findAllByOwner(
	            originalPoll.getOwner()).iterator());
	    Assert.assertEquals(ENTITY_COUNT, polls.size());
//	    Assert.assertEquals(0, entityComparator.compare(originalPoll, polls.get(0)));
	}
}
