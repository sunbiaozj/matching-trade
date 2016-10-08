package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.common.SearchResult;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-context-test.xml")
public class UserGetTradeListTest {

	@Autowired
	private ServiceMockProvider serviceMockProvider;
	private UserService userService;
	
	@Before
	public void before() {
		userService = serviceMockProvider.getUserService();
	}

	@Test
	@Rollback(false)
	public void getTradeLists() {
		UserJson previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
		SearchResult<TradeListJson> responseJson = userService.getTradeLists(previousUserJson.getUserId(), 1, 2);
		assertNotNull(responseJson);
		assertTrue(responseJson.getResultList().size() > 0);
		assertTrue(responseJson.getResultList().size() < 3);
	}
}
