package matchingtrade.service.json;

import java.util.Date;

public class TradeItemJson extends JsonLinkSupport {

	private Integer tradeItemId;
	private String description;
	private String name;
	private Date updatedDateTime;
	
	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public Integer getTradeItemId() {
		return tradeItemId;
	}
	
	public Date getUpdatedDateTime() {
		return updatedDateTime;
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

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
