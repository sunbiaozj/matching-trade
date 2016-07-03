package matchingtrade.service;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;

@Path("/tradeitem")
@Service
public class TradeItemService {

	@Autowired
	TradeItemDao tradeItemDao;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response post(TradeItemJson requestJson) {
    	TradeItemTransformer transformer = new TradeItemTransformer();
    	TradeItemEntity requestEntity  = transformer.transform(requestJson);
    	
    	tradeItemDao.save(requestEntity);

    	TradeItemJson result = transformer.transform(requestEntity);
        return Response.ok().entity(result).build();
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response get() {
    	List<TradeItemJson> result = new ArrayList<>();
    	TradeItemTransformer transformer = new TradeItemTransformer();
    	
    	List<TradeItemEntity> searchResult = tradeItemDao.search();
    	for (TradeItemEntity e : searchResult) {
			result.add(transformer.transform(e));
		}
    	
        return Response.ok().entity(result).build();
    }
    
}
