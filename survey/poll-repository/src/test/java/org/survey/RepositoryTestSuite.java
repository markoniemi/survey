package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.file.repository.FileRepositoryJPATest;
import org.survey.file.repository.FileRepositoryStubTest;
import org.survey.file.repository.FileRepositoryTest;
import org.survey.poll.repository.PollRepositoryJPATest;
import org.survey.poll.repository.PollRepositoryStubTest;
import org.survey.poll.repository.QuestionRepositoryJPATest;
import org.survey.poll.repository.QuestionRepositoryStubTest;
import org.survey.user.model.UserComparatorTest;
import org.survey.user.model.UserTest;
import org.survey.user.repository.UserRepositoryH2Test;
import org.survey.user.repository.UserRepositoryJPATest;
import org.survey.user.repository.UserRepositoryStubTest;
import org.survey.user.repository.UserRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserTest.class,
    UserComparatorTest.class,
    UserRepositoryStubTest.class,
    UserRepositoryTest.class,
    UserRepositoryJPATest.class,
    UserRepositoryH2Test.class,
    FileRepositoryTest.class,
    FileRepositoryJPATest.class,
    FileRepositoryStubTest.class,
    QuestionRepositoryJPATest.class,
    QuestionRepositoryStubTest.class,
    PollRepositoryJPATest.class,
    PollRepositoryStubTest.class,
})
public class RepositoryTestSuite {

}
