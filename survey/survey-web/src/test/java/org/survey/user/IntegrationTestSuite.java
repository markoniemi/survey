package org.survey.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    UnitTestSuite.class, 
    HtmlUnitIT.class, 
    WebDriverIT.class,
    UserServiceWebDriverIT.class,
    UserServiceIT.class,
    UserServiceRestIT.class,
    FileServiceWebDriverIT.class,
    PollServiceWebDriverIT.class,
    FileServiceIT.class
// need to re-write the service tests so that they do not use repositories
//    PollServiceIT.class
})
public class IntegrationTestSuite {
}
