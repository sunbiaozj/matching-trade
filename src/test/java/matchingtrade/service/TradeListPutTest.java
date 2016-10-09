package matchingtrade.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authorization.AuthorizationException;
import matchingtrade.model.TradeListModel;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.StringRandom;
import matchingtrade.test.random.TradeListRandom;
import matchingtrade.test.random.UserRandom;
import matchingtrade.transformer.TradeListTransformer;
import matchingtrade.transformer.UserTransformer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class TradeListPutTest {
	
	@Autowired
	private ServiceMockProvider serviceMockProvider;
	private TradeListService tradeListService;
	@Autowired
	private TradeListModel tradeListModel;
	@Autowired
	private UserModel userModel;
	
	@Before
	public void before() {
		tradeListService = serviceMockProvider.getTradeListService();
	}

	@Test
	@Rollback(false)
	public void get() {
		TradeListJson previousJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		TradeListJson responseJson = tradeListService.get(previousJson.getTradeListId());
		assertNotNull(responseJson);
		assertEquals(previousJson.getName(), responseJson.getName());
		assertEquals(previousJson.getTradeListId(), responseJson.getTradeListId());
	}
	
	@Test
	@Rollback(false)
	public void getUnAuthorized() {
		// Create a new User with a TradeList
		UserJson userJson = new UserRandom().next();
		UserEntity userEntity = new UserTransformer().transform(userJson);
		TradeListJson tradeListJson = new TradeListRandom().next();
		TradeListEntity tradeListEntity = new TradeListTransformer().transform(tradeListJson);
		userEntity.getTradeLists().add(tradeListEntity);
		userEntity.setRole(UserEntity.Role.USER);;
		userModel.save(userEntity);

		boolean throwsException = false;
		try {
			tradeListService.get(tradeListEntity.getTradeListId());
		} catch (AuthorizationException e) {
			throwsException = true;
		}
		assertEquals(true, throwsException);
	}

	@Test
	@Rollback(true)
	public void put() {
		TradeListJson previousTradeListJson = (TradeListJson) IntegrationTestStore.get(TradeListPostTest.class.getSimpleName());
		previousTradeListJson.setName(new StringRandom().nextName());
		TradeListJson responseTradeListJson = tradeListService.put(previousTradeListJson.getTradeListId(), previousTradeListJson);
		assertEquals(previousTradeListJson.getName(), responseTradeListJson.getName());
	}
	
	@Test
	@Rollback(true)
	public void putUnAuthorized() {
		TradeListJson tradeListJson = new TradeListRandom().next();
		TradeListEntity tradeListEntity = new TradeListTransformer().transform(tradeListJson);
		UserJson userJson = new UserRandom().next();
		UserEntity userEntity = new UserTransformer().transform(userJson);
		userEntity.setRole(UserEntity.Role.USER);
		userModel.save(userEntity);
		
		tradeListModel.save(userEntity.getUserId(), tradeListEntity);
		
		boolean throwsException = false;
		try {
			tradeListService.put(tradeListEntity.getTradeListId(), tradeListJson);
		} catch (AuthorizationException e) {
			throwsException = true;
		}
		assertEquals(true, throwsException);
	}
}
