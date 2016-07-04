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
import matchingtrade.test.mocked.UriInfoMocked;
import matchingtrade.test.random.TradeItemRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemPostTest {
	
	@Autowired
	private TradeItemService tradeItemService;

	@Before
	public void setup() {
		tradeItemService.uriInfo = new UriInfoMocked();
	}
	
	@Test
	@Rollback(false)
	public void post() {
		TradeItemJson request = new TradeItemRandom().get();
		TradeItemJson response = (TradeItemJson) tradeItemService.post(request).getEntity();
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTradeItemId());
	}

}
