package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Link;

import matchingtrade.service.TradeItemService;

public class JsonFactory {

	public Set<JsonLink> getLinks(String baseUri, Json json) {
		Set<JsonLink> result = new HashSet<JsonLink>();
		if (json instanceof TradeItemJson) {
			String resourceUri = Link.fromResource(TradeItemService.class).build().getUri().toString();
			JsonLink l = new JsonLink();
			l.setHref(baseUri + resourceUri + "/" + ((TradeItemJson) json).getTradeItemId());
			l.setRel("_self");
			result.add(l);
		}
		return result;
	}

}
