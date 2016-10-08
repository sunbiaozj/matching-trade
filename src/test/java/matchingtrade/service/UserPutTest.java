package matchingtrade.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authorization.AuthorizationException;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.StringRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-context-test.xml")
public class UserPutTest {

	@Autowired
	private ServiceMockProvider serviceMockProvider;
	private UserService userService;
	private UserJson userJson = new UserJson();
	
	@Before
	public void before() {
		userService = serviceMockProvider.getUserService();
		UserJson previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
		userJson.setEmail(previousUserJson.getEmail());
		userJson.setName(previousUserJson.getEmail());
		userJson.setTradeLists(previousUserJson.getTradeLists());
		userJson.setUserId(previousUserJson.getUserId());
	}
	
	@Test
	@Rollback(false)
	public void updateName() {
		
		StringRandom randomString = new StringRandom();
		String newName = randomString.nextName();
		userJson.setName(newName);
		userService.put(userJson.getUserId(), userJson);
		assertEquals(newName, userJson.getName());
	}
	
	@Test
	@Rollback(false)
	public void updateEmail() {
		StringRandom random = new StringRandom();
		String newEmail = random.nextEmail();
		userJson.setEmail(newEmail);
		boolean throwsException = false;
		try {
			userService.put(userJson.getUserId(), userJson);
		} catch (IllegalArgumentException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}

	@Test
	@Rollback(false)
	public void updateUserWithoutUserId() {
		boolean throwsException = false;
		try {
			userJson.setUserId(null);
			userService.put(userJson.getUserId(), userJson);
		} catch (AuthorizationException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
	@Test
	@Rollback(false)
	public void updateNameNotAuthorized() {
		StringRandom random = new StringRandom();
		String newName = random.nextName();
		userJson.setUserId(userJson.getUserId()+1);
		userJson.setName(newName);
		boolean throwsException = false;
		try {
			userService.put(userJson.getUserId(), userJson);
		} catch (AuthorizationException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
}
