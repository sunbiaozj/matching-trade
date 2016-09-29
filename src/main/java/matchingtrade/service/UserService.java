package matchingtrade.service;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.authorization.Authorization;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.TradeListModel;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.transformer.TradeListTransformer;
import matchingtrade.transformer.UserTransformer;
import matchingtrade.validator.UserValidator;

@Path("/users")
@Service
public class UserService {

	@Autowired
	private Authorization authorization;
	private SessionProvider sessionProvider;
	@Autowired
	private TradeListModel tradeListModel;
	@Autowired
	private UserModel model;
	@Autowired
	private UserValidator validator;
	private UserTransformer transformer = new UserTransformer();
	private TradeListTransformer tradeListTransformer = new TradeListTransformer();

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

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{userId}/tradelists")
    public SearchResult<TradeListJson> get(
    		@PathParam("userId") Integer userId,
    		@QueryParam("_page") Integer _page,
    		@QueryParam("_limit") Integer _limit) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	authorization.validateIdentityAndDoBasicAuthorization(sessionProvider.getUserAuthentication(), userId);
		// Validate the request
		validator.validatePagination(_page, _limit);
    	// Transform the request
    	SearchCriteria sc = new SearchCriteria(new Pagination(_page, _limit));
    	sc.addCriterion(UserEntity.Field.userId, sessionProvider.getUserAuthentication().getUserId());
    	// Delegate to model
    	SearchResult<TradeListEntity> searchResultEntity = tradeListModel.search(sc);
    	// Transform the result
    	List<TradeListJson> resultList = new JsonArrayList<TradeListJson>();
    	for (TradeListEntity e : searchResultEntity.getResultList()) {
    		TradeListJson j = tradeListTransformer.transform(e);
    		resultList.add(j);
		}
    	SearchResult<TradeListJson> searchResultJson = new SearchResult<TradeListJson>(resultList, searchResultEntity.getPagination());
        return searchResultJson;
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
    	UserEntity resultEntity = model.save(requestEntity);
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
