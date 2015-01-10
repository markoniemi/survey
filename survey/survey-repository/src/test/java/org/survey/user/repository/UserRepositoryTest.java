package org.survey.user.repository;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.repository.CrudRepositoryTest;
import org.survey.user.model.User;
import org.survey.user.model.UserComparator;
import org.survey.user.model.UserFactory;
import org.survey.user.repository.UserRepository;

/**
 * Test UserRepository. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test-stub.xml")
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
