package matchingtrade.service.json;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TradeListJson extends JsonLinkSupport {

	private Integer tradeListId;
	private String name;
	
	private Set<TradeItemJson> tradeItems = new LinkedHashSet<TradeItemJson>();

	public String getName() {
		return name;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public Set<TradeItemJson> getTradeItems() {
		return tradeItems;
	}

	public Integer getTradeListId() {
		return tradeListId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTradeItems(Set<TradeItemJson> tradeItems) {
		this.tradeItems = tradeItems;
	}

	public void setTradeListId(Integer tradeListId) {
		this.tradeListId = tradeListId;
	}

}
