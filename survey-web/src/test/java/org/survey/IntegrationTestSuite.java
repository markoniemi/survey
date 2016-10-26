package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    UnitTestSuite.class, 
    SurveyWebIT.class,
})
public class IntegrationTestSuite {
}
