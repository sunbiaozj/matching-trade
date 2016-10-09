package matchingtrade.test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.TradeItemService;
import matchingtrade.service.TradeListService;
import matchingtrade.service.UserService;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.random.UserRandom;
import matchingtrade.transformer.UserTransformer;

@Component
public class MockProvider {
	
	SessionProvider sessionProviderMock;
	@Autowired
	private TradeItemService tradeItemService;
	@Autowired
	private TradeListService tradeListService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;

	public TradeItemService getTradeItemService() {
		tradeItemService.setSessionProvider(sessionProviderMock);
		return tradeItemService;
	}
	
	public TradeListService getTradeListService() {
		tradeListService.setSessionProvider(sessionProviderMock);
		return tradeListService;
	}

	public UserService getUserService() {
		userService.setSessionProvider(sessionProviderMock);
		return userService;
	}
	
	public UserJson getNewUser() {
		UserJson newUserJson = new UserRandom().next();
		UserTransformer userTransformer = new UserTransformer();
		UserEntity newUserEntity = userTransformer.transform(newUserJson);
		userDao.save(newUserEntity);
		UserJson result = userTransformer.transform(newUserEntity);
		return result;
	}
	
	public void initNewUserService() {
		// Create a UserJson and put in IntegrationTestStore
		UserJson userJson = getNewUser();
		IntegrationTestStore.put(UserJson.class.getSimpleName(), userJson);
		
		// Mock SessionProvicer
		sessionProviderMock = Mockito.mock(SessionProvider.class);
		UserAuthentication userAuthentication = new UserAuthentication();
		userAuthentication.setAuthenticated(true);
		userAuthentication.setEmail(userJson.getEmail());
		userAuthentication.setName(userJson.getName());
		userAuthentication.setNewUser(true);
		userAuthentication.setUserId(userJson.getUserId());
		Mockito.when(sessionProviderMock.getUserAuthentication()).thenReturn(userAuthentication);
		IntegrationTestStore.put(SessionProvider.class.getSimpleName(), sessionProviderMock);
	}
	
}
