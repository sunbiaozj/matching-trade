package matchingtrade.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.TradeListRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class TradeListPostTest {

	@Autowired
	private MockProvider serviceMockProvider;
	private UserService userService;
	
	@Before
	public void before() {
		userService = serviceMockProvider.getUserService();
	}
	
	@Test
	@Rollback(false)
	public void post() {
		TradeListJson requestJson = new TradeListRandom().next();
		UserJson previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
		TradeListJson responseJson = userService.postTradeLists(previousUserJson.getUserId(), requestJson);
		Assert.assertNotNull(responseJson);
		Assert.assertNotNull(responseJson.getTradeListId());
		// Store it so it can be reused in other tests
		IntegrationTestStore.put(TradeListPostTest.class.getSimpleName(), responseJson);
	}
	
}
