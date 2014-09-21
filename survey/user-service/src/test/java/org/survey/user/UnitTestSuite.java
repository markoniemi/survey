package org.survey.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.user.service.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceImplTest.class
})
public class UnitTestSuite {

}
