package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.model.user.UserComparatorTest;
import org.survey.model.user.UserTest;
import org.survey.repository.file.FileRepositoryJPATest;
import org.survey.repository.file.FileRepositoryTest;
import org.survey.repository.poll.PollRepositoryJPATest;
import org.survey.repository.poll.QuestionRepositoryTest;
import org.survey.repository.user.UserRepositoryJPATest;
import org.survey.repository.user.UserRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserTest.class,
    UserComparatorTest.class,
    UserRepositoryTest.class,
    UserRepositoryJPATest.class,
    FileRepositoryTest.class,
    FileRepositoryJPATest.class,
        // question repository jpa test makes no sense with crud test, since
        // deleting a question should nullify the parent poll first.
        //    QuestionRepositoryJPATest.class,
    QuestionRepositoryTest.class,
    PollRepositoryJPATest.class,
})
public class RepositoryTestSuite {

}
