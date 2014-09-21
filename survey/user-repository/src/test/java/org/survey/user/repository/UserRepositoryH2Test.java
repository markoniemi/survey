package org.survey.user.repository;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test UserRepository using H2 database. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-UserRepositoryJPATest-H2.xml")
@Ignore("Ignored until JBoss fix that enables multiple persistence units in persistence.xml")
public class UserRepositoryH2Test extends UserRepositoryTest {
}
