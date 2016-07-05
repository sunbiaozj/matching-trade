package matchingtrade.service;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.JsonFactory;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;

@Path("/tradeitem")
@Service
public class TradeItemService {
	
	@Autowired
	TradeItemDao tradeItemDao;
	
	private TradeItemTransformer transformer = new TradeItemTransformer();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response post(TradeItemJson requestJson) {
    	TradeItemEntity requestEntity  = transformer.transform(requestJson);
    	tradeItemDao.save(requestEntity);
    	TradeItemJson result = transformer.transform(requestEntity);
        return Response.ok(result).build();
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response get() {
    	List<TradeItemJson> result = new ArrayList<>();
    	List<TradeItemEntity> searchResult = tradeItemDao.search();
    	for (TradeItemEntity e : searchResult) {
    		TradeItemJson j = transformer.transform(e);
    		result.add(j);
		}
        return Response.ok().entity(result).build();
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
