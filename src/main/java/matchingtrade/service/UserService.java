package matchingtrade.service;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;
import matchingtrade.transformer.UserTransformer;
import matchingtrade.validator.UserValidator;

@Path("/users")
@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	private UserTransformer transformer = new UserTransformer();
	private UserValidator validator = new UserValidator();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public UserJson post(UserJson requestJson) {
    	validator.validatePost(requestJson);
    	UserEntity requestEntity = transformer.transform(requestJson);
    	userDao.save(requestEntity);
    	UserJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public UserJson put(UserJson requestJson) {
    	validator.validatePut(requestJson);
    	UserEntity requestEntity = userDao.get(requestJson.getUserId());
    	transformer.transform(requestJson, requestEntity);
    	userDao.save(requestEntity);
    	UserJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{userId}")
    public UserJson get(@PathParam("userId") Integer userId) {
    	UserEntity userEntity = userDao.get(userId);
    	UserJson result = transformer.transform(userEntity);
        return result;
    }
}
