package org.survey.repository.file;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.survey.RepositoryJpaTestConfig;

/**
 * Test FileRepository using database. Must use SpringJUnit4ClassRunner to
 * enable spring injection. Loaded Spring configuration is defined by
 * ContextConfiguration.
 * 
 * @see org.survey.repository.file.FileRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryJpaTestConfig.class))
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class FileRepositoryJPATest extends FileRepositoryTest {
}
