package matchingtrade.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@SuiteClasses({
		TradeItemPostTest.class,
		TradeItemGetTest.class,
		TradeListPostTest.class,
		UserPostTest.class,
		UserGetTest.class
	})

@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeSuite {

} 