package matchingtrade.service;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.authentication.User;
import matchingtrade.common.util.SessionProvider;
import matchingtrade.model.TradeListModel;
import matchingtrade.persistence.dao.TradeListDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.transformer.TradeItemTransformer;
import matchingtrade.transformer.TradeListTransformer;

@Path("/tradelists")
@Service
public class TradeListService {
	
	private SessionProvider sessionProvider;
	@Autowired
	private TradeListModel model;
	@Autowired
	private TradeListDao tradeListDao;
	private TradeListTransformer transformer = new TradeListTransformer();
	
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public List<TradeListJson> get() {    	
    	List<TradeListEntity> searchResult = tradeListDao.getAll();
    	
    	TradeListTransformer transformer = new TradeListTransformer();
    	List<TradeListJson> result = new JsonArrayList<TradeListJson>();
    	for (TradeListEntity e : searchResult) {
			result.add(transformer.transform(e));
		}
    	
        return result;
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeListId}")
    public TradeListJson get(@PathParam("tradeListId") Integer tradeListId) {
    	// Validate the request
    	// TODO: Add validation here
    	// Delegate to model layer
    	TradeListEntity tradeListEntity = model.get(tradeListId);
    	// Transform the response
    	TradeListTransformer transformer = new TradeListTransformer();
		return transformer.transform(tradeListEntity);
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeListId}/tradeitems/")
    @Transactional
    public List<TradeItemJson> getTradeItems(@PathParam("tradeListId") Integer tradeListId) {
    	List<TradeItemJson> result = new JsonArrayList<>();
    	TradeListEntity tradeListEntity = tradeListDao.get(tradeListId);
    	TradeItemTransformer transformer = new TradeItemTransformer();
    	for (TradeItemEntity e : tradeListEntity.getTradeItems()) {
    		result.add(transformer.transform(e));
    	}
		return result;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeListJson post(TradeListJson requestJson) {
    	// Validate the request
    	// TODO: Add validation here
    	// Transform the request
    	TradeListEntity requestEntity = transformer.transform(requestJson);
    	// Delegate to model layer
    	User user = sessionProvider.getUser();
    	TradeListEntity resultEntity = model.post(user, requestEntity);
    	// Transform the response
    	TradeListJson resultJson = transformer.transform(resultEntity);
        return resultJson;
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
