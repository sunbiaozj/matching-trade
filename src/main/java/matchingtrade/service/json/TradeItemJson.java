package matchingtrade.service.json;

public class TradeItemJson extends Json {

	private Integer tradeItemId;
	private String description;
	private String name;
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

	public Integer getTradeItemId() {
		return tradeItemId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTradeItemId(Integer tradeItemId) {
		this.tradeItemId = tradeItemId;
	}

}
