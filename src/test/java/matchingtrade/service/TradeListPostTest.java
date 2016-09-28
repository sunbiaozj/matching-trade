package matchingtrade.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.TradeListRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/application-context-test.xml", "/application-context-web.xml" })
public class TradeListPostTest {
	
	private SessionProvider sessionProviderMock;
	@Autowired
	private TradeListService service;
	
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
	public void post() {
		TradeListJson requestJson = new TradeListRandom().next();
		TradeListJson responseJson = service.post(requestJson);
		Assert.assertNotNull(responseJson);
		Assert.assertNotNull(responseJson.getTradeListId());
		Assert.assertNotNull(responseJson.getTradeItems());
		
		// Store it so it can be reused in other tests
		IntegrationTestStore.put(TradeListPostTest.class.getSimpleName(), responseJson);
	}
	
}
