package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.common.SearchResult;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.MockProvider;
import matchingtrade.validator.ValidationException;

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
	
	@Test
	public void getWithPaginationPositive() {
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		SearchResult<TradeItemJson> response = tradeItemService.get(previousTradeListJson.getTradeListId(), 1, 3);
		List<TradeItemJson> result = response.getResultList();
		assertNotNull(result);
		assertTrue(result.size() > 0 && result.size() <= 3);
	}

	@Test
	public void getWithPaginationNegativeLimit() {
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		boolean throwsException = false;
		try {
			tradeItemService.get(previousTradeListJson.getTradeListId(), 1, -1);
		} catch (ValidationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
	}

	@Test
	public void getWithPaginationNegativePage() {
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		boolean throwsException = false;
		try {
			tradeItemService.get(previousTradeListJson.getTradeListId(), -3, 3);
		} catch (ValidationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
}
