package matchingtrade.service.json;

public class TradeListJson {

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

	@Override
	public String toString() {
		return "TradeListJson [tradeListId=" + tradeListId + ", name=" + name
				+ "]";
	}
}
