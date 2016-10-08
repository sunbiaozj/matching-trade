package matchingtrade.service;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.StringRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class TradeItemPutTest {

	@Autowired
	private TradeItemService service;

	@Test
	@Rollback(false)
	public void putPositive() {
		TradeItemJson previousTradeItemJson = (TradeItemJson) IntegrationTestStore.get(TradeListPostTradeItemTest.class.getSimpleName());
		StringRandom random = new StringRandom();
		TradeItemJson requestJson = new TradeItemJson();
		requestJson.setTradeItemId(previousTradeItemJson.getTradeItemId());
		requestJson.setName(random.nextName());
		requestJson.setDescription(random.nextDescription());
		TradeItemJson responseJson = service.put(requestJson);
		assertNotEquals(previousTradeItemJson.getDescription(), responseJson.getDescription());
		assertNotEquals(previousTradeItemJson.getName(), responseJson.getName());
	}
	
}
