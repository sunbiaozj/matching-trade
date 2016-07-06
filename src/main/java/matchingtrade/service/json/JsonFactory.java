package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Link;

import matchingtrade.service.TradeItemService;
import matchingtrade.service.TradeListService;

public class JsonFactory {

	public Set<JsonLink> getLinks(String baseUri, Json json) {
		Set<JsonLink> result = new HashSet<JsonLink>();
		if (json instanceof TradeItemJson) {
			String resourceUri = Link.fromResource(TradeItemService.class).build().getUri().toString();
			JsonLink l = new JsonLink();
			l.setHref(baseUri + resourceUri + "/" + ((TradeItemJson) json).getTradeItemId());
			l.setRel("_self");
			result.add(l);
			
			return result;
		}
		
		if (json instanceof TradeListJson) {
			String tradeListUri = Link.fromResource(TradeListService.class).build().getUri().toString();
			JsonLink self = new JsonLink();
			self.setHref(baseUri + tradeListUri + "/" + ((TradeListJson) json).getTradeListId());
			self.setRel("_self");
			result.add(self);
			
			String tradeItemUri = Link.fromResource(TradeItemService.class).build().getUri().toString();
			JsonLink tradeItem = new JsonLink();
			tradeItem.setHref(baseUri + tradeItemUri);
			tradeItem.setRel("tradeItem");
			result.add(tradeItem);
			
			return result;
		}
		
		
		if (json instanceof JsonResponse) {
			JsonLink l = new JsonLink();
			l.setHref(baseUri);
			l.setRel("_self");
			result.add(l);
		}
		return result;
	}

}
