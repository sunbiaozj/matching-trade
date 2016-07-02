package matchingtrade.service;

import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.random.TradeItemRandom;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemPostTest {
	
	@Autowired
	private TradeItemService tradeItemService;

	@Test
	@Rollback(false)
	public void post() {
		TradeItemJson request = new TradeItemRandom().get();
		TradeItemJson response = (TradeItemJson) tradeItemService.post(request).getEntity();
		
		
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTradeItemId());
	}

}
