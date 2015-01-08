package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.file.repository.FileRepositoryJPATest;
import org.survey.file.repository.FileRepositoryStubTest;
import org.survey.file.repository.FileRepositoryTest;
import org.survey.poll.repository.PollRepositoryJPATest;
import org.survey.poll.repository.PollRepositoryStubTest;
import org.survey.poll.repository.QuestionRepositoryStubTest;
import org.survey.user.model.UserComparatorTest;
import org.survey.user.model.UserTest;
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
    FileRepositoryTest.class,
    FileRepositoryJPATest.class,
    FileRepositoryStubTest.class,
        // question repository jpa test makes no sense with crud test, since
        // deleting a question should nullify the parent poll first.
        //    QuestionRepositoryJPATest.class,
    QuestionRepositoryStubTest.class,
    PollRepositoryJPATest.class,
    PollRepositoryStubTest.class,
})
public class RepositoryTestSuite {

}
