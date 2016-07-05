package matchingtrade.service;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.dao.TradeListDao;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.transformer.TradeListTransformer;

@Path("/tradelist")
@Service
public class TradeListService {

	@Autowired
	TradeListDao tradeListDao;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeListJson post(TradeListJson requestJson) {
    	TradeListTransformer transformer = new TradeListTransformer();
    	TradeListEntity requestEntity = transformer.transform(requestJson);
    	
    	tradeListDao.save(requestEntity);

    	TradeListJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public List<TradeListJson> get() {    	
    	List<TradeListEntity> searchResult = tradeListDao.search();
    	
    	TradeListTransformer transformer = new TradeListTransformer();
    	List<TradeListJson> result = new JsonArrayList<TradeListJson>();
    	for (TradeListEntity e : searchResult) {
			result.add(transformer.transform(e));
		}
    	
        return result;
    }
    
}
