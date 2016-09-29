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
import matchingtrade.validator.ValidationException;

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
		TradeItemJson previousJson = (TradeItemJson) IntegrationTestStore.get(TradeItemJson.class.getSimpleName());
		TradeItemJson response = service.get(previousJson.getTradeItemId());
		assertNotNull(response);
		assertNotNull(response.getName());
		assertNotNull(response.getDescription());
	}	
	
	@Test
	@Rollback(false)
	public void getWithPaginationPositive() {
		SearchResult<TradeItemJson> response = service.get(1, 3);
		List<TradeItemJson> result = response.getResultList();
		assertNotNull(result);
		assertTrue(result.size() > 0 && result.size() <= 3);
	}

	@Test
	@Rollback(false)
	public void getWithPaginationNegativeLimit() {
		boolean throwsException = false;
		try {
			service.get(1, -1);
		} catch (ValidationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
	}

	@Test
	@Rollback(false)
	public void getWithPaginationNegativePage() {
		boolean throwsException = false;
		try {
			service.get(-3, 3);
		} catch (ValidationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
}
