package matchingtrade.service.json;

public class TradeItemJson {

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

	@Override
	public String toString() {
		return "tradeItemId=" + tradeItemId + ", name=" + name + ", description=" + description;
	}
}
