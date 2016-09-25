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

import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.model.TradeItemModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.transformer.TradeItemTransformer;

@Path("/tradeitems")
@Service
public class TradeItemService {
	
	@Autowired
	TradeItemModel tradeItemModel;
	
	private TradeItemTransformer transformer = new TradeItemTransformer();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson post(TradeItemJson requestJson) {
    	TradeItemEntity requestEntity = transformer.transform(requestJson);
    	tradeItemModel.save(requestEntity);
    	TradeItemJson result = transformer.transform(requestEntity);
        return result;
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public TradeItemJson put(TradeItemJson requestJson) {
    	TradeItemEntity requestEntity = tradeItemModel.get(requestJson.getTradeItemId());
    	transformer.transform(requestJson, requestEntity);
    	tradeItemModel.save(requestEntity);
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
    	SearchCriteria sc = new SearchCriteria(new Pagination(_page, _limit));
    	SearchResult<TradeItemEntity> searchResultEntity = tradeItemModel.search(sc);
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
    	TradeItemEntity tradeItemEntity = tradeItemModel.get(tradeItemId);
    	TradeItemJson result = transformer.transform(tradeItemEntity);
        return result;
    }
}
