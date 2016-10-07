package matchingtrade.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.authorization.AuthorizationException;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-test.xml", "/application-context-web.xml"})
public class UserGetTest {

	@Autowired
	private UserService userService;
	
	@Test
	@Rollback(false)
	public void get() {
		UserAuthentication user = (UserAuthentication) IntegrationTestStore.get(UserAuthentication.class.getSimpleName());
		UserJson userJson = userService.get(user.getUserId());
		assertNotNull(userJson);
		assertNotNull(userJson.getUserId());
		assertNotNull(userJson.getName());
		assertNotNull(userJson.getEmail());
		IntegrationTestStore.put(UserJson.class.getSimpleName(), userJson);
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
