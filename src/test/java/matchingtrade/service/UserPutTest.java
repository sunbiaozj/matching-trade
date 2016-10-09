package matchingtrade.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authorization.AuthorizationException;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.MockProvider;
import matchingtrade.test.random.StringRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-context-test.xml")
public class UserPutTest {

	@Autowired
	private MockProvider mockProvider;
	private UserService userService;
	private UserJson previousUserJson = new UserJson();
	
	@Before
	public void before() {
		mockProvider.initNewUserService();
		userService = mockProvider.getUserService();
		previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
	}
	
	@Test
	public void putName() {
		String newName = UserPutTest.class.getName() + ".newName";
		previousUserJson.setName(newName);
		userService.put(previousUserJson.getUserId(), previousUserJson);
		assertEquals(newName, previousUserJson.getName());
	}
	
	@Test
	public void putEmail() {
		StringRandom random = new StringRandom();
		String newEmail = random.nextEmail();
		previousUserJson.setEmail(newEmail);
		boolean throwsException = false;
		try {
			userService.put(previousUserJson.getUserId(), previousUserJson);
		} catch (IllegalArgumentException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}

	@Test
	public void putUserWithoutUserId() {
		boolean throwsException = false;
		try {
			previousUserJson.setUserId(null);
			userService.put(previousUserJson.getUserId(), previousUserJson);
		} catch (AuthorizationException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
	@Test
	public void putNotAuthorized() {
		UserJson newUser = mockProvider.getNewUser();
		boolean throwsException = false;
		try {
			userService.put(newUser.getUserId(), newUser);
		} catch (AuthorizationException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
	@Test
	public void putInvalidUserId() {
		UserJson newUser = mockProvider.getNewUser();
		boolean throwsException = false;
		try {
			userService.put(previousUserJson.getUserId(), newUser);
		} catch (IllegalArgumentException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}

}
