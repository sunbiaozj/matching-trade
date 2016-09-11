package matchingtrade.authentication;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used for testing and development purposes.
 * It will not send an authentication request to the authentication authority.
 * Instead it is going to return to "/webui/#/authentication/existing-user" allowing development and testing
 * without doing to the authentication authority.
 * 
 * @author rafael.santos.bra@gmail.com
 *
 */
public class AuthenticationRequestRedirectTestExistingUser implements AuthenticationRequestRedirect {
	public void sendAuthenticationRedirect(HttpServletRequest request, HttpServletResponse response, String state, String clientId, String redirectURI)
			throws MalformedURLException, IOException {
		response.sendRedirect("/webui/#/authentication/existing-user");
	}
}
