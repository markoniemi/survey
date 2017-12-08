package org.survey.repository.user;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.survey.RepositoryJpaTestConfig;

/**
 * Test UserRepository using database. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 * @see org.survey.repository.user.UserRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryJpaTestConfig.class))
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class UserRepositoryJPATest extends UserRepositoryTest {
}
