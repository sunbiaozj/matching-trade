package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-test.xml", "/application-context-web.xml"})
public class UserGetTradeListTest {

	private SessionProvider sessionProviderMock;
	@Autowired
	private UserService service;
	
	@Before
	public void before() {
		sessionProviderMock = Mockito.mock(SessionProvider.class);
		Mockito
			.when(sessionProviderMock.getUserAuthentication())
			.thenReturn((UserAuthentication)IntegrationTestStore.get(UserAuthentication.class.getSimpleName()));
		service.setSessionProvider(sessionProviderMock);
	}
	
	@Test
	@Rollback(false)
	public void getTradeLists() {
		UserAuthentication previousUserAuthentication = (UserAuthentication)IntegrationTestStore.get(UserAuthentication.class.getSimpleName());
		SearchResult<TradeListJson> responseJson = service.getTradeLists(previousUserAuthentication.getUserId(), 1, 2);
		assertNotNull(responseJson);
		assertTrue(responseJson.getResultList().size() > 0);
	}
}
