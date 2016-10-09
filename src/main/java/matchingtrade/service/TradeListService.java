package matchingtrade.service;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import matchingtrade.authorization.TradeListAuthorization;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.TradeItemModel;
import matchingtrade.model.TradeListModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.transformer.TradeItemTransformer;
import matchingtrade.transformer.TradeListTransformer;

@Api(value = "/tradelists")
@Path("/tradelists")
@Service
public class TradeListService {
	
	@Autowired
	TradeListAuthorization authorization;
	private SessionProvider sessionProvider;
	@Autowired
	private TradeListModel tradeListModel;
	private TradeListTransformer tradeListTransformer = new TradeListTransformer();
	@Autowired
	private TradeItemModel tradeItemModel;
	private TradeItemTransformer tradeItemTransformer = new TradeItemTransformer();
	
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeListId}")
    public TradeListJson get(@PathParam("tradeListId") Integer tradeListId) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	authorization.authorizeResource(sessionProvider.getUserAuthentication().getUserId(), tradeListId);
    	// Delegate to model layer
    	TradeListEntity tradeListEntity = tradeListModel.get(tradeListId);
    	// Transform the response
    	TradeListJson result = tradeListTransformer.transform(tradeListEntity);
		return result;
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeListId}/tradeitems")
    public TradeItemJson postTradeItems(@PathParam("tradeListId") Integer tradeListId, TradeItemJson requestJson) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	// Validate the request
    	// TODO: Add validation here
    	// Transform the request
    	TradeItemEntity requestEntity = tradeItemTransformer.transform(requestJson);
    	// Delegate to model layer
    	TradeItemEntity resultEntity = tradeItemModel.save(tradeListId, requestEntity);
    	// Transform the response
    	TradeItemJson resultJson = tradeItemTransformer.transform(resultEntity);
        return resultJson;
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeListId}")
	public TradeListJson put(@PathParam("tradeListId") Integer tradeListId, TradeListJson requestJson) {
    	// Check authorization for this operation
    	authorization.doBasicAuthorization(sessionProvider.getUserAuthentication());
    	authorization.authorizeResource(sessionProvider.getUserAuthentication().getUserId(), tradeListId);
    	// Validate the request
    	// TODO: Add validation here
    	// Transform the request
    	TradeListEntity tradeListEntity = tradeListModel.get(tradeListId);
    	tradeListTransformer.transform(requestJson, tradeListEntity);
    	// Delegate to model layer
    	tradeListModel.save(tradeListEntity);
    	// Transform the response
    	TradeListJson result = tradeListTransformer.transform(tradeListEntity);
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
