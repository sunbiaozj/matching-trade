package matchingtrade.service.json;

public class TradeListJson extends JsonLinkSupport {

	private Integer tradeListId;
	private String name;

	public String getName() {
		return name;
	}

	public Integer getTradeListId() {
		return tradeListId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTradeListId(Integer tradeListId) {
		this.tradeListId = tradeListId;
	}

}
