package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Link;

import matchingtrade.service.TradeItemService;
import matchingtrade.service.TradeListService;
import matchingtrade.service.UserService;

public class JsonFactory {

	private JsonLink buildLinks(String baseUri) {
		JsonLink self = new JsonLink();
		self.setHref(baseUri);
		self.setRel("_self");
		return self;
	}

	private void buildLinks(String baseUri, Set<JsonLink> result, TradeItemJson jsonAsTradeItem) {
		String resourceUri = Link.fromResource(TradeItemService.class).build().getUri().toString();
		JsonLink self = new JsonLink();
		self.setHref(baseUri + resourceUri + "/" + jsonAsTradeItem.getTradeItemId());
		self.setRel("_self");
		result.add(self);
	}

	private void buildLinks(String baseUri, Set<JsonLink> result, TradeListJson jsonAsTradeList) {
		String tradeListUri = Link.fromResource(TradeListService.class).build().getUri().toString();
		JsonLink self = new JsonLink();
		self.setHref(baseUri + tradeListUri + "/" + jsonAsTradeList.getTradeListId());
		self.setRel("_self");
		result.add(self);
		
		JsonLink tradeItem = new JsonLink();
		tradeItem.setHref(baseUri + tradeListUri + "/" + jsonAsTradeList.getTradeListId() + "/" + "tradeitems");
		tradeItem.setRel("tradeItems");
		result.add(tradeItem);
	}
	
	private void buildLinks(String baseUri, Set<JsonLink> result, UserJson jsonAsUserJson) {
		String resourceUri = Link.fromResource(UserService.class).build().getUri().toString();
		JsonLink self = new JsonLink();
		self.setHref(baseUri + resourceUri + "/" + jsonAsUserJson.getUserId());
		self.setRel("_self");
		result.add(self);
	}

	public Set<JsonLink> getLinks(String baseUri, Json json) {
		// Result to be returned
		Set<JsonLink> result = new HashSet<JsonLink>();
		
		// Build links for TradeItemJson
		if (json instanceof TradeItemJson) {
			TradeItemJson jsonAsTradeItem = (TradeItemJson) json;
			buildLinks(baseUri, result, jsonAsTradeItem);
			return result;
		}

		// Build links for TradeListJson
		if (json instanceof TradeListJson) {
			TradeListJson jsonAsTradeList = (TradeListJson) json;
			buildLinks(baseUri, result, jsonAsTradeList);
			return result;
		}
		
		// Build links for UserJson
		if (json instanceof UserJson) {
			UserJson jsonAsUserJson = (UserJson) json;
			buildLinks(baseUri, result, jsonAsUserJson);
			return result;
		}
		
		// Build links for JsonResponse
		if (json instanceof JsonResponse) {
			JsonLink self = buildLinks(baseUri);
			result.add(self);
		}
		return result;
	}

}
