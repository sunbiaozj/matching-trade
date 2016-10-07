package matchingtrade.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@SuiteClasses({
		UserPutTest.class,
		UserGetTest.class,
		UserPostTradeListsTest.class,
		TradeListPostTest.class,
		TradeListGetTest.class,
		TradeListPostTradeItemTest.class,
		TradeItemGetTest.class,
		TradeItemPutTest.class
	})

@ContextConfiguration(locations={"/application-context-web.xml"})
public class TestingSuite {

} 