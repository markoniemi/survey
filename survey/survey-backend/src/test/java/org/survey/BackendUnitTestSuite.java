package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.security.JwtAuthenticationFilterTest;
import org.survey.security.JwtTokenTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    JwtAuthenticationFilterTest.class,
    JwtTokenTest.class
})
public class BackendUnitTestSuite {
}
