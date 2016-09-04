package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.service.file.FileServiceImplTest;
import org.survey.service.poll.PollServiceImplTest;
import org.survey.service.user.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceImplTest.class,
    FileServiceImplTest.class,
    PollServiceImplTest.class
})
public class ServiceTestSuite {

}
