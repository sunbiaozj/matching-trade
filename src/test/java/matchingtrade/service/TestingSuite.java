package matchingtrade.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@SuiteClasses({
		UserPutTest.class,
		UserGetTest.class,
		TradeListPostTest.class,
		TradeListGetTest.class,
		TradeItemPostTest.class,
		TradeItemGetTest.class
	})

@ContextConfiguration(locations={"/application-context-web.xml"})
public class TestingSuite {

} 