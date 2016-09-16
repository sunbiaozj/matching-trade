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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.SearchCriteria;
import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;

@Path("/tradeitems")
@Service
public class TradeItemService {
	
	@Autowired
	TradeItemDao tradeItemDao;
	
	private TradeItemTransformer transformer = new TradeItemTransformer();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson post(TradeItemJson requestJson) {
    	TradeItemEntity requestEntity = transformer.transform(requestJson);
    	
    	tradeItemDao.save(requestEntity);
    	
    	TradeItemJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson put(TradeItemJson requestJson) {
    	TradeItemEntity requestEntity = tradeItemDao.get(requestJson.getTradeItemId());
    	transformer.transform(requestJson, requestEntity);
    	
    	tradeItemDao.save(requestEntity);
    	
    	TradeItemJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public List<TradeItemJson> get(
    		@QueryParam("_page") Integer _page,
    		@QueryParam("_limit") Integer _limit) {
    	SearchCriteria sc = new SearchCriteria(_page, _limit);
    	List<TradeItemEntity> searchResult = tradeItemDao.get(sc);
    	List<TradeItemJson> result = new JsonArrayList<TradeItemJson>();
    	for (TradeItemEntity e : searchResult) {
    		TradeItemJson j = transformer.transform(e);
    		result.add(j);
		}
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{tradeItemId}")
    public TradeItemJson get(@PathParam("tradeItemId") Integer tradeItemId) {
    	TradeItemEntity tradeItemEntity = tradeItemDao.get(tradeItemId);
    	TradeItemJson result = transformer.transform(tradeItemEntity);
        return result;
    }
}
