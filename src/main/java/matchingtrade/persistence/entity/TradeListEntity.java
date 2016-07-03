package matchingtrade.persistence.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trade_list")
public class TradeListEntity {

	private Integer tradeListId;
	private String name;
	private Set<TradeItemEntity> tradeItems = new LinkedHashSet<TradeItemEntity>();

	@Column(name = "name", length = 120, nullable = false, unique = false)
	public String getName() {
		return name;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(
		name = "trade_list_to_trade_item",
		joinColumns = @JoinColumn(name = "a"),
		inverseJoinColumns = @JoinColumn(name = "b"))
	public Set<TradeItemEntity> getTradeItems() {
		return tradeItems;
	}

	@Id
	@Column(name = "trade_list_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getTradeListId() {
		return tradeListId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTradeItems(Set<TradeItemEntity> tradeItems) {
		this.tradeItems = tradeItems;
	}

	public void setTradeListId(Integer tradeListId) {
		this.tradeListId = tradeListId;
	}

}
