package matchingtrade.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import matchingtrade.configuration.AuthenticationProperties;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.util.ApplicationContextProvider;

public class AuthenticationCallbakServlet extends HttpServlet {
	private static final long serialVersionUID = -6183491052218601077L;
	
	private ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	AuthenticationProperties authenticationProperties = (AuthenticationProperties) context.getBean("authenticationProperties");

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
		String accessToken = obtainAccessToken(request.getParameter("code"));
		
		// 5. Obtain user information from the ID token
		Map<String, Object> userDetailMap = obtainUserInformation(accessToken);		
		System.out.println("User's information. name: "+ userDetailMap.get("name") +" email: " + userDetailMap.get("email") + "; email_verified: " + userDetailMap.get("verified_email"));
		
		// 6. Authenticate the user
		request.getSession().setAttribute("email", userDetailMap.get("email"));
		request.getSession().setAttribute("name", userDetailMap.get("name"));
		request.getSession().setAttribute("isEmailVerified", userDetailMap.get("verified_email"));
		request.getSession().setAttribute("isAuthenticated", true);
		
		// 7. Update user information in the local database
		UserEntity userEntity = updateUserInfo(userDetailMap.get("email").toString(), userDetailMap.get("name").toString());
		if (userEntity != null) {
			request.getSession().setAttribute("userId", userEntity.getUserId());
		}
		boolean isNewUser = true;
		if (userEntity.getUserId() != null) {
			isNewUser = false;
		}
		
		// Done. Let's redirect the request
		String userStatusPathParam = isNewUser ? "new-user" : "existing-user";
		response.sendRedirect("/webui/#/authentication/" + userStatusPathParam);
	}

	/*
	 * Check if there is a user for the given email.
	 * If not, then save a new user in the local database.
	 * If yes, then return the exiting user.
	 * 
	 * @return updated UserEntity.
	 */
	private UserEntity updateUserInfo(String email, String name) {
		UserDao userDao = (UserDao) context.getBean(UserDao.class);
		UserEntity userEntity = userDao.get(email);
		if (userEntity == null) {
			UserEntity u = new UserEntity();
			u.setEmail(email);
			u.setName(name);
			userDao.save(u);
			return u;
		} else {
			return userEntity;
		}
	}

	private String obtainAccessToken(String codeParameter) throws IOException, ClientProtocolException {
		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("https")
			        .setHost("www.googleapis.com")
			        .setPath("/oauth2/v4/token")
			        .setParameter("code", codeParameter)
			        .setParameter("client_id", authenticationProperties.getClientId())
			        .setParameter("client_secret", authenticationProperties.getClientSecret())
			        .setParameter("redirect_uri", authenticationProperties.getRedirectURI())
			        .setParameter("grant_type", "authorization_code" )
			        .build();
		} catch (URISyntaxException e) {
			System.err.println("Error building uri. Exception message: " + e.getMessage() );
		}
		
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpPostResponse = httpClient.execute(httpPost);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpPostResponse.getEntity().getContent()));
		StringBuilder responseString = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			responseString.append(line);
		}
		System.out.println(responseString.toString());
		
		// Returns only the 'access_token' from responseString
		ObjectMapper jacksonObjectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String,Object> accessMap = jacksonObjectMapper.readValue(responseString.toString(), Map.class);
		String accessToken = (String) accessMap.get("access_token");
		return accessToken;
	}

	private Map<String, Object> obtainUserInformation(String accessToken) throws IOException, ClientProtocolException, JsonParseException, JsonMappingException {
		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("https")
			        .setHost("www.googleapis.com")
			        .setPath("/userinfo/v2/me")
			        .build();
		} catch (URISyntaxException e) {
			System.err.println("Error building uri. Exception message: " + e.getMessage() );
		}
		
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Authorization", "Bearer "+accessToken);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpPostResponse = httpClient.execute(httpGet);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpPostResponse.getEntity().getContent()));
		StringBuilder respString = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			respString.append(line);
		}
		System.out.println(respString.toString());
		
		ObjectMapper jacksonObjectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String,Object> userDetailMap = jacksonObjectMapper.readValue(respString.toString(), Map.class);
		return userDetailMap;
	}
}
