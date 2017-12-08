package org.survey.repository.user;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.RepositoryStubTestConfig;
import org.survey.model.user.User;
import org.survey.model.user.UserComparator;
import org.survey.model.user.UserFactory;
import org.survey.repository.CrudRepositoryTest;

/**
 * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryStubTestConfig.class))
public class UserRepositoryTest extends CrudRepositoryTest<User, Long> {
	@Resource
	protected UserRepository userRepository;

	@Override
	public UserRepository getEntityRepository() {
		return userRepository;
	}

	@Before
	public void setUp() {
		entityFactory = new UserFactory();
		entityComparator = new UserComparator();
	}
	
	@After
	public void tearDown() {
	    userRepository.deleteAll();
	}

	@Test
	public void findByEmail() {
		save();
		for (int i = 0; i < ENTITY_COUNT; i++) {
			User foundEntity = userRepository.findByEmail(savedEntities.get(i)
					.getEmail());
			assertEntity(orginalEntities.get(i), foundEntity);
		}
		Assert.assertNull(userRepository.findByEmail("non-existent"));
	}
}
