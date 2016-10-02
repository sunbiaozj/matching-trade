package matchingtrade.authentication;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;

import matchingtrade.common.util.ApplicationContextProvider;
import matchingtrade.configuration.AuthenticationProperties;

public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 373664290851751809L;
	
	private static final ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	private static final AuthenticationProperties authenticationProperties = (AuthenticationProperties) context.getBean("authenticationProperties");
	private static final AuthenticationOAuth authenticationOAuth = (AuthenticationOAuth) context.getBean("authenticationOAuth");

	
	/**
	 * Delegates the request to the correct action.
	 * If request.getRequestURI() ends in 'sing-out' it ends the session.
	 * Otherwise proceeds with the regular user authentication process.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationAction targetAction = getAuthenticationAction(request);
		if (targetAction == null) {
			response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
		} else if (targetAction == AuthenticationAction.SIGNOUT) {
			singOut(request, response);
		} else if (targetAction == AuthenticationAction.AUTHENTICATE) {
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
		String requestUri = request.getRequestURI();
		// Remove tailing slash if any
		if (requestUri.lastIndexOf("/") == requestUri.length()-1) {
			requestUri = requestUri.substring(0, requestUri.length()-1);
		}
		
		int lastPathIndex = requestUri.lastIndexOf("/");
		if (lastPathIndex >= 0) {
			String lastPath = requestUri.substring(lastPathIndex+1);
			result = AuthenticationAction.get(lastPath);
		}
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
		response.setStatus(Response.Status.OK.getStatusCode());
	}
}