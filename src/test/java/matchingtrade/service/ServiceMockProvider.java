package matchingtrade.service;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.UserRandom;
import matchingtrade.transformer.UserTransformer;

@Component
public class ServiceMockProvider {
	
	private boolean isInitialized = false;
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
		init();
		tradeItemService.setSessionProvider(sessionProviderMock);
		return tradeItemService;
	}
	
	public TradeListService getTradeListService() {
		init();
		tradeListService.setSessionProvider(sessionProviderMock);
		return tradeListService;
	}

	public UserService getUserService() {
		init();
		userService.setSessionProvider(sessionProviderMock);
		return userService;
	}
	
	@Transactional
	@Rollback(false)
	public void init() {
		if (isInitialized) {
			return;
		}
		// Create a UserJson and put in IntegrationTestStore
		UserRandom userRandom = new UserRandom();
		UserJson userJson = userRandom.next();
		UserTransformer userTransformer = new UserTransformer();
		UserEntity userEntity = userTransformer.transform(userJson);
		userEntity.setRole(UserEntity.Role.USER);
		userDao.save(userEntity);
		userJson = userTransformer.transform(userEntity);
		IntegrationTestStore.put(UserJson.class.getSimpleName(), userJson);
		
		// Mock SessionProvicer
		sessionProviderMock = Mockito.mock(SessionProvider.class);
		UserAuthentication userAuthenticationMock = new UserAuthentication();
		userAuthenticationMock.setAuthenticated(true);
		userAuthenticationMock.setEmail(userJson.getEmail());
		userAuthenticationMock.setName(userJson.getName());
		userAuthenticationMock.setNewUser(true);
		userAuthenticationMock.setUserId(userJson.getUserId());
		Mockito.when(sessionProviderMock.getUserAuthentication()).thenReturn(userAuthenticationMock);
		IntegrationTestStore.put(SessionProvider.class.getSimpleName(), sessionProviderMock);
		
		isInitialized = true;
	}
	
}
