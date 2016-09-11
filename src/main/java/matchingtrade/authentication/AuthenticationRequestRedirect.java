package matchingtrade.authentication;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationRequestRedirect {
	public void sendAuthenticationRedirect(
			HttpServletRequest request,
			HttpServletResponse response,
			String state, String clientId,
			String redirectURI)
			throws MalformedURLException, IOException;
}
