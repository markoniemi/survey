package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.service.file.FileServiceTest;
import org.survey.service.file.FileServiceTestBase;
import org.survey.service.poll.PollServiceTest;
import org.survey.service.user.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceTest.class,
    FileServiceTest.class,
    PollServiceTest.class
})
public class ServiceTestSuite {

}
