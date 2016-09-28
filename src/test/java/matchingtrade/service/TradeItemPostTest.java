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
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.TradeItemRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemPostTest {
	
	private SessionProvider sessionProviderMock;
	@Autowired
	private TradeItemService service;

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
		TradeItemJson request = new TradeItemRandom().get();
		TradeItemJson response = service.post(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTradeItemId());
	}

}
