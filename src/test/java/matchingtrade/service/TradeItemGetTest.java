package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.IntegrationTestStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class TradeItemGetTest {
	
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
	public void get() {
		SearchResult<TradeItemJson> response = service.get(1, 3);
		List<TradeItemJson> result = response.getResultList();
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

}
