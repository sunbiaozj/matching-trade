package matchingtrade.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.TradeItemRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class TradeListPostTradeItemTest {

	@Autowired
	private ServiceMockProvider serviceMockProvider;
	private TradeListService tradeListService;
	
	@Before
	public void before() {
		tradeListService = serviceMockProvider.getTradeListService();
	}

	@Test
	@Rollback(false)
	public void post() {
		TradeItemJson request = new TradeItemRandom().next();
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		TradeItemJson response = tradeListService.post(previousTradeListJson.getTradeListId(), request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTradeItemId());
		IntegrationTestStore.put(TradeListPostTradeItemTest.class.getSimpleName(), response);
	}

}
