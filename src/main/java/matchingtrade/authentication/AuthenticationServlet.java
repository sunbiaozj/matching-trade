package matchingtrade.authentication;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import matchingtrade.configuration.AuthenticationProperties;
import matchingtrade.util.ApplicationContextProvider;
import matchingtrade.util.JsonUtil;

public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 373664290851751809L;
	
	private static final ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	private static final AuthenticationProperties authenticationProperties = (AuthenticationProperties) context.getBean("authenticationProperties");
	private static final AuthenticationOAuth authenticationOAuth = (AuthenticationOAuth) context.getBean("authenticationOAuth");

	
	/**
	 * Delegates the request to the correct action.
	 * If request.getRequestURI() ends in 'info' it returns information about the session.
	 * If request.getRequestURI() ends in 'sing-out' it ends the session.
	 * Otherwise proceeds with the regular user authentication process.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationAction targetAction = getAuthenticationAction(request);
		if (targetAction == AuthenticationAction.INFO) {
			response.getWriter().append(getAuthenticationInfo(request, response));
		} else if (targetAction == AuthenticationAction.SIGNOUT) {
			singOut(request, response);
		} else {
			redirectToAuthenticationServer(request, response);
		}
	}

	private String generateAntiForgeryToken() {
	  return new BigInteger(130, new SecureRandom()).toString(32);
	}

	/**
	 * Returns the corresponding AuthenticationAction for this request
	 */
	private AuthenticationAction getAuthenticationAction(HttpServletRequest request) {
		AuthenticationAction result = null;
		int lastPathIndex = request.getRequestURI().lastIndexOf("/");
		if (lastPathIndex > 0) {
			String lastPath = request.getRequestURI().substring(lastPathIndex+1);
			result = AuthenticationAction.get(lastPath);
		}
		
		if (result == null) {
			return AuthenticationAction.AUTHENTICATE;
		} else {
			return result;
		}
	}

	private String getAuthenticationInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
//		Map <String, Object> resultMap = new HashMap<>();
//		resultMap.put("userId", request.getSession().getAttribute("userId"));
//		resultMap.put("email", request.getSession().getAttribute("email"));
//		resultMap.put("name", request.getSession().getAttribute("name"));
//		resultMap.put("isAuthenticated", request.getSession().getAttribute("isAuthenticated")==null?false:true );
//		resultMap.put("sessionId", request.getSession().getId());
//		String result = JsonUtil.toJson(resultMap);
		String result = JsonUtil.toJson(user);
		return result;
	}

	private void redirectToAuthenticationServer(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		// 1. Create an anti-forgery state token
		String state = generateAntiForgeryToken();
		request.getSession().setAttribute("authenticationState", state);

		// 2. Send an authentication request to Google
		authenticationOAuth.redirectToAuthorizationServer(response, state, authenticationProperties.getClientId(), authenticationProperties.getRedirectURI());
	}
	
	private void singOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect("/");
	}
}