package matchingtrade.service.util;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Link;

import matchingtrade.service.RestfulService;
import matchingtrade.service.TradeItemService;
import matchingtrade.service.json.Json;
import matchingtrade.service.json.JsonLink;
import matchingtrade.service.json.TradeItemJson;

public class JsonFactory {

	public static Set<JsonLink> getLinks(RestfulService restfulService, Json json) {
		String baseUri = restfulService.getUriInfo().getBaseUri().toString();//http://localhost:8080/matching-trade/services/mt
		Set<JsonLink> result = new HashSet<JsonLink>();
		if (restfulService instanceof TradeItemService && json instanceof TradeItemJson) {
			String resourceUri = Link.fromResource(TradeItemService.class).build().getUri().toString();
			JsonLink l = new JsonLink();
			l.setHref(baseUri + resourceUri + "/" +((TradeItemJson)json).getTradeItemId());
			l.setRel("_self");
			result.add(l);
		}
		return result;
	}
	
	public static Set<JsonLink> getLinks(RestfulService restfulService, Json json, boolean updateJsonLinks) {
		Set<JsonLink> result = getLinks(restfulService, json);
		if (updateJsonLinks) {
			json.set_links(result);
		}
		return result;
	}	

}
