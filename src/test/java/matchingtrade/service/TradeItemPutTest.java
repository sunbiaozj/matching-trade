package matchingtrade.service;

import static org.junit.Assert.assertNotEquals;

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
import matchingtrade.test.random.StringRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemPutTest {
	
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
	public void putPositive() {
		TradeItemJson previousJson = (TradeItemJson) IntegrationTestStore.get(TradeItemJson.class.getSimpleName());
		StringRandom random = new StringRandom();
		TradeItemJson requestJson = new TradeItemJson();
		requestJson.setTradeItemId(previousJson.getTradeItemId());
		requestJson.setName(random.nextName());
		requestJson.setDescription(random.nextDescription());
		TradeItemJson responseJson = service.put(requestJson);
		assertNotEquals(previousJson.getDescription(), responseJson.getDescription());
		assertNotEquals(previousJson.getName(), responseJson.getName());
		IntegrationTestStore.put(TradeItemJson.class.getSimpleName(), responseJson);
	}
	
}
