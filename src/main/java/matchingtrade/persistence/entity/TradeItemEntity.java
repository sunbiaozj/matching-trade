package matchingtrade.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trade_item")
public class TradeItemEntity {

	private Integer tradeItemId;
	private String name;
	private String description;
	
	@Column(name="description", length=500, nullable=true, unique=false)
	public String getDescription() {
		return description;
	}

	@Column(name="name", length=120, nullable=false, unique=false)
	public String getName() {
		return name;
	}

	@Id
	@Column(name="trade_item_id")
	@GeneratedValue
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
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		} else if (!(other instanceof TradeListEntity)) {
			return false;
		}
		TradeItemEntity otherCasted = (TradeItemEntity) other;
		if (tradeItemId != null && otherCasted.getTradeItemId() != null) {
			return  false;
		} else if (!tradeItemId.equals(otherCasted.getTradeItemId())){
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int salt = 1;
		if (tradeItemId != null) {
			salt = tradeItemId;
		}
		return super.hashCode() * salt;
	}
	
}
