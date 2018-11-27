package org.survey.repository.poll;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.survey.config.RepositoryJpaTestConfig;

/**
 * Test PollRepository using database. Must use SpringJUnit4ClassRunner to
 * enable spring injection. Loaded Spring configuration is defined by
 * ContextConfiguration.
 * 
 * @see org.survey.repository.poll.PollRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryJpaTestConfig.class))
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@Transactional
public class PollRepositoryJPATest extends PollRepositoryTest {
}
