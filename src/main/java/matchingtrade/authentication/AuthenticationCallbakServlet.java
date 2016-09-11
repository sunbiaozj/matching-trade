package matchingtrade.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import matchingtrade.configuration.AuthenticationProperties;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.util.ApplicationContextProvider;

public class AuthenticationCallbakServlet extends HttpServlet {
	private static final long serialVersionUID = -6183491052218601077L;
	
	private static final ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	private static final AuthenticationProperties authenticationProperties = (AuthenticationProperties) context.getBean("authenticationProperties");
	private static final AuthenticationOAuth authenticationOAuth = (AuthenticationOAuth) context.getBean("authenticationOAuth");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3. Confirm anti-forgery state token
		String stateParameter = request.getParameter("state");
		String stateAttribute = (String) request.getSession().getAttribute("authenticationState");

		System.out.println("stateParameter: " + stateParameter + "; authenticationStateAttribute: " + stateAttribute);
		// Return HTTP-STATUS 401 if anti-forgery state token does not match
		if (stateAttribute != null && !stateAttribute.equals(stateParameter)) {
			response.setStatus(401);
			request.getSession().invalidate();
			return;
		}
		
		// 4. Exchange code for access token and ID token
		String accessToken = authenticationOAuth.obtainAccessToken(
				request.getParameter("code"),
				authenticationProperties.getClientId(),
				authenticationProperties.getClientSecret(),
				authenticationProperties.getRedirectURI());
		
		// 5. Obtain user information from the ID token
		User user = authenticationOAuth.obtainUserInformation(accessToken);	
		
		// 6. Authenticate the user
		// Update user information in the local database
		user = updateUserInfo(user.getEmail(), user.getName());
		// Put the user in the session
		user.setAuthenticated(true);
		request.getSession().setAttribute("user", user);
		
		// Done. Let's redirect the request
		String userStatusPathParam = user.isNewUser() ? "new-user" : "existing-user";
		response.sendRedirect("/webui/#/authentication/" + userStatusPathParam);
	}

	/*
	 * Check if there is a user for the given email.
	 * If not, then save a new user in the local database.
	 * If yes, then return the information in the local database for the exiting user.
	 * 
	 * @return updated User.
	 */
	private User updateUserInfo(String email, String name) {
		UserDao userDao = (UserDao) context.getBean(UserDao.class);
		UserEntity userEntity = userDao.get(email);
		boolean isNewUser = false;
		if (userEntity == null) {
			userEntity = new UserEntity();
			userEntity.setEmail(email);
			userEntity.setName(name);
			userDao.save(userEntity);
			isNewUser = true;
		}
		User result = new User();
		result.setUserId(userEntity.getUserId());
		result.setEmail(userEntity.getEmail());
		result.setNewUser(isNewUser);
		result.setName(userEntity.getName());
		return result;
	}
}
