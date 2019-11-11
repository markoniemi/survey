package org.survey.repository.user;

import javax.annotation.Resource;

import org.junit.Test;
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
import org.survey.model.user.User;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = RepositoryJpaTestConfig.class))
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup(connection = "dataSource", value = "classpath:dbunit-UserRepositoryDBUnitTest.xml")
@DatabaseSetup(connection = "dataSource", value = "classpath:dbunit-UserRepositoryJPATest.xml", type = DatabaseOperation.REFRESH)
@DatabaseTearDown("classpath:dbunit-UserRepositoryDBUnitTest.xml")
public class UserRepositoryDBUnitTest {
    @Resource
    UserRepository userRepository;

    @Test
    public void test() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
