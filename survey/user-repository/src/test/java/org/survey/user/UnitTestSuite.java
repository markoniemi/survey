package org.survey.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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
    UserRepositoryH2Test.class
})
public class UnitTestSuite {

}
