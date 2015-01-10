package org.survey.poll.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test PollRepository using database. Must use SpringJUnit4ClassRunner to
 * enable spring injection. Loaded Spring configuration is defined by
 * ContextConfiguration.
 * 
 * @see org.survey.poll.repository.PollRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test-jpa.xml")
public class PollRepositoryJPATest extends PollRepositoryTest {
}
