package matchingtrade.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.configuration.AuthenticationProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context-test.xml")
public class AuthenticationCallbakServletTest {
	
	@Autowired
	AuthenticationProperties authenticationProperties;
	
	@Test
	public void doGetAtiForgeryTokenNotMatch() throws ServletException, IOException {
		AuthenticationCallbakServlet authenticationCallbakServlet = new AuthenticationCallbakServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("state", "stateParameter");
		request.getSession().setAttribute(AuthenticationProperties.Token.ANTI_FORGERY_STATE.toString(), "stateAttribute");
		MockHttpServletResponse response = new MockHttpServletResponse();
		authenticationCallbakServlet.doGet(request, response);
		assertEquals(401, response.getStatus());
		assertNull(request.getSession(false));
	}
	
	@Test
	public void doGetAtiForgeryTokenMatch() throws ServletException, IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("state", "identicalStateMock");
		request.getSession().setAttribute(AuthenticationProperties.Token.ANTI_FORGERY_STATE.toString(), "identicalStateMock");
		
		// AuthenticationCallbakServlet.AuthenticationOAuth is defined as AuthenticationOAuthTestExistingUserMock on matchandtrade-config.xml
		AuthenticationCallbakServlet authenticationCallbakServlet = new AuthenticationCallbakServlet();
		authenticationCallbakServlet.doGet(request, response);

		UserAuthentication responseUserAuthentication = (UserAuthentication) request.getSession(false).getAttribute("user");
		assertNotNull(responseUserAuthentication.getUserId());
		assertTrue(responseUserAuthentication.isAuthenticated());
		assertEquals("AuthenticationOAuthTestExistingUserMock", responseUserAuthentication.getName());
		assertEquals("AuthenticationOAuthTestExistingUserMock@mock.com", responseUserAuthentication.getEmail());
	}
	
}
