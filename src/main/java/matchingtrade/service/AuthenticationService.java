package matchingtrade.service;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import matchingtrade.authentication.UserAuthentication;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.UserEntity;

@Api(value = "/authentication")
@Path("/authentication")
@Service
public class AuthenticationService {
	
	private SessionProvider sessionProvider;
	@Autowired
	private UserModel userModel;
	
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/")
	public UserAuthentication get() {
		UserAuthentication userAuthentication = sessionProvider.getUserAuthentication();
		UserAuthentication result = getAuthenticatedUser(userAuthentication);
		return result;
	}
	
	private UserAuthentication getAuthenticatedUser(UserAuthentication userAuthentication) {
		UserAuthentication result = new UserAuthentication();
		// If session does not contain information from the user, then return an empty JSON string.
		if (userAuthentication == null || userAuthentication.getUserId() == null) {
			return result;
		}
		// Get user information from model
		else {
			UserEntity userEntity = userModel.get(userAuthentication.getUserId());
			if (userEntity != null) {
				result.setEmail(userEntity.getEmail());
				result.setName(userEntity.getName());
				result.setAuthenticated(userAuthentication.isAuthenticated());
				result.setNewUser(userAuthentication.isNewUser());
				result.setUserId(userAuthentication.getUserId());
			}
			return result;
		}
	}
	
	/**
	 * Used for dependency injection by the web container using JAX-RS
	 */
	@Context
	public void setContext(MessageContext context){
		sessionProvider = new SessionProvider(context);
	}
	
	public void setSessionProvider(SessionProvider sessionProvider) {
		this.sessionProvider = sessionProvider;
	}

}
