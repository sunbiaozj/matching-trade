package matchingtrade.service;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.authorization.AuthorizationException;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.StringRandom;
import matchingtrade.test.random.UserRandom;
import matchingtrade.transformer.UserTransformer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-test.xml", "/application-context-web.xml"})
public class UserPostTradeListsTest {

	@Autowired
	private UserService service;

	@Test
	@Rollback(false)
	@Before
	public void postTradeLists() {
		UserAuthentication user = (UserAuthentication) IntegrationTestStore.get(UserAuthentication.class.getSimpleName());
		TradeListJson request = new TradeListJson();
		StringRandom stringRandom = new StringRandom();
		request.setName(stringRandom.nextName());
		TradeListJson response = service.postTradeLists(user.getUserId(), request);
		assertNotNull(response.getTradeListId());
	}

}
