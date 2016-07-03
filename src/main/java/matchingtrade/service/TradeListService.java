package matchingtrade.service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import matchingtrade.persistence.dao.TradeListDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.transformer.TradeListTransformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Path("/tradeitem")
@Service
public class TradeListService {

	@Autowired
	TradeListDao tradeListDao;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response post(TradeListJson requestJson) {
    	TradeListTransformer transformer = new TradeListTransformer();
    	TradeListEntity requestEntity = new TradeListEntity();
    	requestEntity = transformer.transform(requestJson);
    	
//    	
//    	BeanUtils.copyProperties(requestJson, requestEntity, "tradeItems");
//    	BeanUtils.copyProperties(requestJson.getTradeItems(), requestEntity.getTradeItems());
//    	
    	
    	tradeListDao.save(requestEntity);
    	
    	TradeListJson result = new TradeListJson();
    	BeanUtils.copyProperties(requestEntity, result);
    	
        return Response.ok().entity(result).build();
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response get() {
    	List<TradeItemJson> result = new ArrayList<>();
    	
    	List<TradeListEntity> searchResult = tradeListDao.search();
    	for (TradeListEntity e : searchResult) {
    		TradeItemJson j = new TradeItemJson();
			BeanUtils.copyProperties(e, j);
			result.add(j);
		}
    	
        return Response.ok().entity(result).build();
    }
    
}
