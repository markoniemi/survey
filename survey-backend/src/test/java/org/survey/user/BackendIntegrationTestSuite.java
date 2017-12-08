package org.survey.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
// TODO refactor IT tests to org.survey package
public class BackendIntegrationTestSuite {
}
