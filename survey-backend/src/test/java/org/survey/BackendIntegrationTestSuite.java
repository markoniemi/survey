package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.service.file.FileServiceIT;
import org.survey.service.file.FileServiceRestIT;
import org.survey.service.file.FileServiceWebDriverIT;
import org.survey.service.poll.PollServiceIT;
import org.survey.service.poll.PollServiceWebDriverIT;
import org.survey.service.user.UserServiceIT;
import org.survey.service.user.UserServiceRestIT;
import org.survey.service.user.UserServiceWebDriverIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    UserServiceWebDriverIT.class,
    UserServiceIT.class,
    UserServiceRestIT.class,
    FileServiceWebDriverIT.class,
    PollServiceWebDriverIT.class,
    FileServiceIT.class,
    FileServiceRestIT.class,
    PollServiceIT.class,
    // TODO fix service and enable test case
//    PollServiceRestIT.class
})
public class BackendIntegrationTestSuite {
}
