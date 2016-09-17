package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.common.SearchResult;
import matchingtrade.service.json.TradeItemJson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemGetTest {
	
	@Autowired
	private TradeItemService tradeItemService;
	
	@Test
	@Rollback(false)
	public void get() {
		SearchResult<TradeItemJson> response = tradeItemService.get(null, null);
		List<TradeItemJson> result = response.getResultList();
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

}
