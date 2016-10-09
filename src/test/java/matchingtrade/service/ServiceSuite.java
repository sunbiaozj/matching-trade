package matchingtrade.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		UserGetTest.class,
		UserPutTest.class,
		
		UserPostTradeListsTest.class,
		UserGetTradeListTest.class,
		
		TradeListPostTest.class,
		TradeListGetTest.class,
		TradeListPutTest.class,
		
		TradeListPostTradeItemTest.class,
		TradeItemGetTest.class,
		TradeItemPutTest.class
	})

public class ServiceSuite {

}