package matchingtrade.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.random.TradeListRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context-web.xml" })
public class TradeListPostTest {

	@Autowired
	private TradeListService tradeListService;

	@Test
	@Rollback(false)
	public void post() {
		TradeListJson requestJson = new TradeListRandom().get();
		TradeListJson responseJson = tradeListService.post(requestJson);

		Assert.assertNotNull(responseJson);
		Assert.assertNotNull(responseJson.getTradeListId());
		Assert.assertNotNull(responseJson.getTradeItems());
	}

}
