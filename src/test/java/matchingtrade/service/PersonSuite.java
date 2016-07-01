package matchingtrade.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

import mtrade.PersonTest;

@RunWith(Suite.class)
@SuiteClasses({
	PersonTest.class
	})
@ContextConfiguration(locations={"/application-context-web.xml"})
public class PersonSuite {

} 