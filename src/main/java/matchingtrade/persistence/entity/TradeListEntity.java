package matchingtrade.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trade_list")
public class TradeListEntity {

	@Id
	@Column(name="trade_list_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer tradeListId;
	private String name;
	
	@Column(name="description", length=120, nullable=false, unique=false)
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
		return "TradeListEntity [tradeListId=" + tradeListId + ", name=" + name
				+ "]";
	}

}
