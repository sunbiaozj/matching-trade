package matchingtrade.service;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.authorization.TradeItemAuthorization;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.TradeItemModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;

@Path("/tradeitems")
@Service
public class TradeItemService {
	
	@Autowired
	private TradeItemAuthorization authorization;
	private SessionProvider sessionProvider;
	@Autowired
	private TradeItemModel model;
	private TradeItemTransformer transformer = new TradeItemTransformer();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson post(TradeItemJson requestJson) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	// Validate the request
    	// TODO add validation
    	// Transform the request
    	TradeItemEntity requestEntity = transformer.transform(requestJson);
    	// Delegate to model layer
    	model.save(requestEntity);
    	// Transform the response
    	TradeItemJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson put(TradeItemJson requestJson) {
    	// Check authorization for this operation
    	authorization.verifyIfUserIsAuthorizedOverTheResource(sessionProvider.getUserAuthentication(), requestJson.getTradeItemId());
    	// Validate the request
    	// TODO add validation
    	// Transform the request
    	TradeItemEntity requestEntity = model.get(requestJson.getTradeItemId());
    	transformer.transform(requestJson, requestEntity);
    	// Delegate to model layer
    	model.save(requestEntity);
    	// Transform the response
    	TradeItemJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public SearchResult<TradeItemJson> get(
    		@QueryParam("_page") Integer _page,
    		@QueryParam("_limit") Integer _limit) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	// Transform the request
    	SearchCriteria sc = new SearchCriteria(new Pagination(_page, _limit));
    	sc.addCriterion(UserEntity.Field.userId, sessionProvider.getUserAuthentication().getUserId());
    	// Delegate to model
    	SearchResult<TradeItemEntity> searchResultEntity = model.search(sc);
    	// Transform the result
    	List<TradeItemJson> resultList = new JsonArrayList<TradeItemJson>();
    	for (TradeItemEntity e : searchResultEntity.getResultList()) {
    		TradeItemJson j = transformer.transform(e);
    		resultList.add(j);
		}
    	SearchResult<TradeItemJson> searchResultJson = new SearchResult<TradeItemJson>(resultList, searchResultEntity.getPagination());
        return searchResultJson;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeItemId}")
    public TradeItemJson get(@PathParam("tradeItemId") Integer tradeItemId) {
    	// Check authorization for this operation
    	authorization.verifyIfUserIsAuthorizedOverTheResource(sessionProvider.getUserAuthentication(), tradeItemId);
    	// Delegate to model
    	TradeItemEntity tradeItemEntity = model.get(tradeItemId);
    	// Transform the response
    	TradeItemJson result = transformer.transform(tradeItemEntity);
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
