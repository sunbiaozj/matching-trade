package matchingtrade.service;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;

@Path("/tradeitem")
@Service
public class TradeItemService {

	@Autowired
	TradeItemDao tradeItemDao;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response post(TradeItemJson inputJson) {
    	TradeItemEntity inputEntity = new TradeItemEntity();
    	BeanUtils.copyProperties(inputJson, inputEntity);
    	tradeItemDao.save(inputEntity);
    	
    	TradeItemJson result = new TradeItemJson();
    	BeanUtils.copyProperties(inputEntity, result);
    	
        return Response.ok().entity(result).build();
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response get() {
    	List<TradeItemJson> result = new ArrayList<>();
    	
    	
    	List<TradeItemEntity> searchResult = tradeItemDao.search();
    	for (TradeItemEntity e : searchResult) {
    		TradeItemJson j = new TradeItemJson();
			BeanUtils.copyProperties(e, j);
			result.add(j);
		}
    	
    	
        return Response.ok().entity(result).build();
    }
    
}
