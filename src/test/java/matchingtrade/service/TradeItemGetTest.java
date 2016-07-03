package matchingtrade.service;

import java.util.List;

import matchingtrade.service.json.TradeItemJson;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemGetTest {
	
	@Autowired
	private TradeItemService tradeItemService;

	@Test
	@Rollback(false)
	public void get() {
		List<TradeItemJson> responseEntity = (List<TradeItemJson>) tradeItemService.get().getEntity();
		Assert.assertNotNull(responseEntity);
		Assert.assertTrue(responseEntity.size() > 0);
	}

}
