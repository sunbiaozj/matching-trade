package matchingtrade.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import matchingtrade.authentication.UserAuthentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.authorization.AuthorizationException;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.IntegrationTestStore;
import matchingtrade.test.random.RandomString;
import matchingtrade.test.random.UserRandom;
import matchingtrade.transformer.UserTransformer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-test.xml", "/application-context-web.xml"})
public class UserPutTest {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	private UserJson userJson;
	
	@Transactional
	@Rollback(false)
	@Before
	public void before() {
		// Create a user which is going to be updated via PUT
		UserRandom userRandom = new UserRandom();
		userJson = userRandom.next();
		UserTransformer userTransformer = new UserTransformer();
		UserEntity userEntity = userTransformer.transform(userJson);
		userEntity.setRole(UserEntity.Role.USER);
		userDao.save(userEntity);
		userJson = userTransformer.transform(userEntity);
		
		
		// Mock SessionProvicer
		SessionProvider sessionProviderMock = mock(SessionProvider.class);
		UserAuthentication userMocked = new UserAuthentication();
		userMocked.setAuthenticated(true);
		userMocked.setEmail(userJson.getEmail());
		userMocked.setName(userJson.getName());
		userMocked.setNewUser(true);
		userMocked.setUserId(userJson.getUserId());
		when(sessionProviderMock.getUserAuthentication()).thenReturn(userMocked);
		
		userService.setSessionProvider(sessionProviderMock);
		
		// Store it so it can be reused in other tests
		IntegrationTestStore.put(UserAuthentication.class.getSimpleName(), userMocked);
	}
	
	@Test
	@Rollback(false)
	public void updateName() {
		RandomString randomString = new RandomString();
		String newName = randomString.nextName();
		userJson.setName(newName);
		userService.put(userJson);
		assertEquals(newName, userJson.getName());
	}
	
	@Test
	@Rollback(false)
	public void updateEmail() {
		RandomString random = new RandomString();
		String newEmail = random.nextEmail();
		userJson.setEmail(newEmail);
		boolean throwsException = false;
		try {
			userService.put(userJson);
		} catch (IllegalArgumentException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}

	@Test
	@Rollback(false)
	public void updateNameNotAuthorized() {
		RandomString random = new RandomString();
		String newName = random.nextName();
		userJson.setUserId(userJson.getUserId()+1);
		userJson.setName(newName);
		boolean throwsException = false;
		try {
			userService.put(userJson);
		} catch (AuthorizationException e){
			throwsException = true;
		}
		assertTrue(throwsException);
	}
	
}
