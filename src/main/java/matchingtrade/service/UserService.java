package matchingtrade.service;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.authorization.Authorization;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;
import matchingtrade.transformer.UserTransformer;
import matchingtrade.validator.UserValidator;

@Path("/users")
@Service
public class UserService {

	@Autowired
	private Authorization authorization;
	private SessionProvider sessionProvider;
	@Autowired
	private UserModel model;
	@Autowired
	private UserValidator validator;
	private UserTransformer transformer = new UserTransformer();

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/{userId}")
	public UserJson get(@PathParam("userId") Integer userId) {
		// Check authorization for this operation
		authorization.validateIdentityAndDoBasicAuthorization(sessionProvider.getUserAuthentication(), userId);
		// Delegate to model layer
		UserEntity userEntity = model.get(userId);
		// Transform the response
		UserJson result = transformer.transform(userEntity);
		return result;
	}

	@PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public UserJson put(UserJson requestJson) {
		// Check authorization for this operation
		authorization.validateIdentityAndDoBasicAuthorization(sessionProvider.getUserAuthentication(), requestJson.getUserId());
    	// Validate the request
		validator.validatePut(requestJson);
		// Transform the request
		UserEntity requestEntity = model.get(requestJson.getUserId());
		transformer.transform(requestJson, requestEntity);
    	// Delegate to model layer
    	UserEntity resultEntity = model.put(requestEntity);
    	// Transform the response
    	UserJson result = transformer.transform(resultEntity);
        return result;
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
