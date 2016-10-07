package matchingtrade.service;

import static org.junit.Assert.*;

import matchingtrade.authentication.UserAuthentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.common.util.SessionProvider;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/application-context-test.xml", "/application-context-web.xml" })
public class TradeListGetTest {

	@Autowired
	private TradeListService service;

	@Test
	@Rollback(false)
	public void get() {
		TradeListJson previousJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		TradeListJson responseJson = service.get(previousJson.getTradeListId());
		assertNotNull(responseJson);
		assertEquals(previousJson.getName(), responseJson.getName());
		assertEquals(previousJson.getTradeListId(), responseJson.getTradeListId());
	}

}
