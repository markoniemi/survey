package org.survey.file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.file.repository.FileRepositoryJPATest;
import org.survey.file.repository.FileRepositoryStubTest;
import org.survey.file.repository.FileRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    FileRepositoryTest.class,
    FileRepositoryJPATest.class,
    FileRepositoryStubTest.class
})
public class UnitTestSuite {

}
