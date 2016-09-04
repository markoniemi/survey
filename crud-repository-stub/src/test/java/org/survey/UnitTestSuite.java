package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.repository.BeanHelperTest;
import org.survey.repository.CrudRepositoryStubTest;
import org.survey.repository.TestEntityRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	BeanHelperTest.class,
	CrudRepositoryStubTest.class,
	TestEntityRepositoryTest.class
})
public class UnitTestSuite {

}
