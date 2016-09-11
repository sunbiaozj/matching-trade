package matchingtrade.authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

public class AuthenticationRequestRedirectGoogle implements AuthenticationRequestRedirect {
	public void sendAuthenticationRedirect(HttpServletRequest request, HttpServletResponse response, String state, String clientId, String redirectURI)
			throws MalformedURLException, IOException {
		request.getSession().setAttribute("authenticationState", state);
		
		// 2. Send an authentication request to Google
		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("https")
			        .setHost("accounts.google.com")
			        .setPath("/o/oauth2/v2/auth")
			        .setParameter("client_id", clientId)
			        .setParameter("response_type", "code")
			        .setParameter("scope", "openid email profile" )
			        .setParameter("redirect_uri", redirectURI)
			        .setParameter("state", state)
			        .build();
		} catch (URISyntaxException e) {
			System.err.println("Error building uri. Exception message: " + e.getMessage() );
		}
		System.out.println(uri.toURL().toString());
		response.sendRedirect(uri.toURL().toString());
	}
}
