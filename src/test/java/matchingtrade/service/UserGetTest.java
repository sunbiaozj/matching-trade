package matchingtrade.service;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-context-test.xml")
public class UserGetTest {

	@Autowired
	private MockProvider mockProvider;
	private UserService userService;

	@Before
	public void before() {
		mockProvider.initNewUserService();
		userService = mockProvider.getUserService();
	}
	
	@Test
	@Rollback(false)
	public void get() {
		UserJson previousUserJson = (UserJson) IntegrationTestStore.get(UserJson.class.getSimpleName());
		UserJson userJson = userService.get(previousUserJson.getUserId());
		assertNotNull(userJson);
		assertEquals(previousUserJson.getUserId(), userJson.getUserId());
		assertEquals(previousUserJson.getName(), userJson.getName());
		assertEquals(previousUserJson.getEmail(), userJson.getEmail());
	}

	@Test
	@Rollback(false)
	public void getInexistingUser() {
		boolean throwsException = false;
		try {
			userService.get(-1000);
		} catch (AuthorizationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
		throwsException = false;
		try {
			userService.get(null);
		} catch (AuthorizationException e) {
			throwsException = true;
		}
		assertTrue(throwsException);
	}
}
