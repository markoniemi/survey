package org.survey.repository.poll;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.RepositoryJpaTestConfig;

/**
 * Test PollRepository using database. Must use SpringJUnit4ClassRunner to
 * enable spring injection. Loaded Spring configuration is defined by
 * ContextConfiguration.
 * 
 * @see org.survey.repository.poll.PollRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryJpaTestConfig.class))
// TODO add transactional and execution listeners
public class PollRepositoryJPATest extends PollRepositoryTest {
}
