package matchingtrade.authentication;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.ApplicationContext;

import matchingtrade.configuration.AuthenticationProperties;
import matchingtrade.util.ApplicationContextProvider;
import matchingtrade.util.JsonUtil;

public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 373664290851751809L;
	
	private ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	private AuthenticationProperties authenticationProperties = (AuthenticationProperties) context.getBean("authenticationProperties");
	
	/**
	 * Delegates the request to the correct action.
	 * If request.getRequestURI() ends in 'info' it returns information about the session.
	 * If request.getRequestURI() ends in 'singout' it ends the session.
	 * Otherwhise proceeds with the regular user authentication process.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationAction targetAction = getAuthenticationAction(request);
		if (targetAction == AuthenticationAction.INFO) {
			response.getWriter().append(getAuthenticationInfo(request, response));
		} else if (targetAction == AuthenticationAction.SIGNOUT) {
			singOut(request, response);
		} else {
			sendAuthenticationRequest(request, response);
		}
	}

	private void singOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect("/");
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

	private void sendAuthenticationRequest(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException {
		// 1. Create an anti-forgery state token
		String state = generateAntiForgeryToken();
		request.getSession().setAttribute("authenticationState", state);
		
		// 2. Send an authentication request to Google
		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("https")
			        .setHost("accounts.google.com")
			        .setPath("/o/oauth2/v2/auth")
			        .setParameter("client_id", authenticationProperties.getClientId())
			        .setParameter("response_type", "code")
			        .setParameter("scope", "openid email profile" )
			        .setParameter("redirect_uri", authenticationProperties.getRedirectURI())
			        .setParameter("state", state)
			        .build();
		} catch (URISyntaxException e) {
			System.err.println("Error building uri. Exception message: " + e.getMessage() );
		}
		System.out.println(uri.toURL().toString());
		response.sendRedirect(uri.toURL().toString());
	}

	private String generateAntiForgeryToken() {
	  return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	private String getAuthenticationInfo(HttpServletRequest request, HttpServletResponse response) {
		Map <String, Object> resultMap = new HashMap<>();
		resultMap.put("userId", request.getSession().getAttribute("userId"));
		resultMap.put("email", request.getSession().getAttribute("email"));
		resultMap.put("name", request.getSession().getAttribute("name"));
		resultMap.put("isAuthenticated", request.getSession().getAttribute("isAuthenticated")==null?false:true );
		resultMap.put("sessionId", request.getSession().getId());
		String result = JsonUtil.toJson(resultMap);
		return result;
	}
}