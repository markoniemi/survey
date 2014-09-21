package org.survey.poll;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.poll.repository.PollRepositoryJPATest;
import org.survey.poll.repository.PollRepositoryStubTest;
import org.survey.poll.repository.QuestionRepositoryJPATest;
import org.survey.poll.repository.QuestionRepositoryStubTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    QuestionRepositoryJPATest.class,
    QuestionRepositoryStubTest.class,
    PollRepositoryJPATest.class,
    PollRepositoryStubTest.class,
})
public class UnitTestSuite {

}
