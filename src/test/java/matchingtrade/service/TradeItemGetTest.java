package matchingtrade.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.MockProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class TradeItemGetTest {
	
	@Autowired
	private MockProvider serviceMockProvider;
	private TradeItemService tradeItemService;
	
	@Before
	public void before() {
		tradeItemService = serviceMockProvider.getTradeItemService();
	}

	@Test
	public void get() {
		TradeItemJson previousTradeItemJson = (TradeItemJson) IntegrationTestStore.get(TradeListPostTradeItemTest.class.getSimpleName());
		TradeItemJson response = tradeItemService.get(previousTradeItemJson.getTradeItemId());
		assertNotNull(response);
		assertNotNull(response.getName());
		assertNotNull(response.getDescription());
	}	
	
}
