package matchingtrade.service;

import static org.junit.Assert.assertNotNull;

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
import matchingtrade.test.random.StringRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-context-test.xml")
public class UserPostTradeListsTest {

	@Autowired
	private ServiceMockProvider serviceMockProvider;
	private UserService userService;
	
	@Before
	public void before() {
		userService = serviceMockProvider.getUserService();
	}

	@Test
	@Rollback(false)
	public void postTradeLists() {
		UserJson previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
		TradeListJson request = new TradeListJson();
		StringRandom stringRandom = new StringRandom();
		request.setName(stringRandom.nextName());
		TradeListJson response = userService.postTradeLists(previousUserJson.getUserId(), request);
		assertNotNull(response.getTradeListId());
	}

}
