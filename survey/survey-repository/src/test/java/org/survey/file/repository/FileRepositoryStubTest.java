package org.survey.file.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-FileRepositoryStubTest.xml")
public class FileRepositoryStubTest extends FileRepositoryTest {
}
