package org.survey.poll.repository;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.poll.model.Poll;
import org.survey.poll.model.PollComparator;
import org.survey.poll.model.PollFactory;
import org.survey.poll.repository.PollRepository;
import org.survey.repository.CrudRepositoryTest;
import org.survey.user.model.User;
import org.survey.user.model.UserFactory;
import org.survey.user.repository.UserRepository;

/**
 * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test-stub.xml")
public class PollRepositoryTest extends CrudRepositoryTest<Poll, Long> {
	@Autowired
	@Qualifier("pollRepository")
	protected PollRepository pollRepository;
    @Autowired
    @Qualifier("userRepository")
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
