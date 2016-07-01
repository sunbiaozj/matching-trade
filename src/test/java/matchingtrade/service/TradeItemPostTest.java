package matchingtrade.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.util.RandomNameGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemPostTest {
	
	@Autowired
	private TradeItemService tradeItemService;

	@Test
	@Rollback(false)
	public void post() {
		TradeItemJson inputJson = new TradeItemJson();
		inputJson.setName(new RandomNameGenerator().get());
		TradeItemJson responseJson = (TradeItemJson) tradeItemService.post(inputJson).getEntity();
		Assert.assertNotNull(responseJson.getTradeItemId());
	}

}
