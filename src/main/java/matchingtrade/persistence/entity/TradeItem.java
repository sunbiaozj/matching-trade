package matchingtrade.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TradeItem")
public class TradeItem {

	@Id
	@Column(name="trade_item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer tradeItemId;
	private String description;
	private String name;
	
	@Column(name="description", length=500, nullable=true, unique=false)
	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", length=120, nullable=false, unique=false)
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "tradeItemId="+tradeItemId+", name="+name+", description="+description;
	}
}
