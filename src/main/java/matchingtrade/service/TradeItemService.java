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

import io.swagger.annotations.Api;
import matchingtrade.authorization.TradeItemAuthorization;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.TradeItemModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;
import matchingtrade.validator.TradeItemValidator;

@Api(value = "/tradeitems")
@Path("/tradeitems")
@Service
public class TradeItemService {
	
	@Autowired
	private TradeItemAuthorization authorization;
	private SessionProvider sessionProvider;
	@Autowired
	private TradeItemModel tradeItemModel;
	private TradeItemTransformer tradeItemTransformer = new TradeItemTransformer();
	@Autowired
	private TradeItemValidator tradeItemValidator;

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeItemId}")
	public TradeItemJson put(@PathParam("tradeItemId") Integer tradeItemId, TradeItemJson requestJson) {
    	// Check authorization for this operation
    	authorization.verifyIfUserIsAuthorizedOverTheResource(sessionProvider.getUserAuthentication(), requestJson.getTradeItemId());
    	// Validate the request
    	tradeItemValidator.validatePut(sessionProvider.getUserAuthentication().getUserId(), tradeItemId, requestJson);
    	// Transform the request
    	TradeItemEntity requestEntity = tradeItemModel.get(requestJson.getTradeItemId());
    	tradeItemTransformer.transform(requestJson, requestEntity);
    	// Delegate to model layer
    	tradeItemModel.save(requestEntity);
    	// Transform the response
    	TradeItemJson result = tradeItemTransformer.transform(requestEntity);
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeItemId}")
    public TradeItemJson get(@PathParam("tradeItemId") Integer tradeItemId) {
    	// Check authorization for this operation
    	authorization.verifyIfUserIsAuthorizedOverTheResource(sessionProvider.getUserAuthentication(), tradeItemId);
    	// Delegate to model
    	TradeItemEntity tradeItemEntity = tradeItemModel.get(tradeItemId);
    	// Transform the response
    	TradeItemJson result = tradeItemTransformer.transform(tradeItemEntity);
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
