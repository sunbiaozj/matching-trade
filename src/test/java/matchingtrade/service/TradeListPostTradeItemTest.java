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
import matchingtrade.service.json.TradeListJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.TradeItemRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeListPostTradeItemTest {
	
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
		TradeItemJson request = new TradeItemRandom().next();
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		TradeItemJson response = service.post(previousTradeListJson.getTradeListId(), request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTradeItemId());
	}

}
