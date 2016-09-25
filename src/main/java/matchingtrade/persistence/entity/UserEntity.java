package matchingtrade.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

	private Integer userId;
	private String email;
	private String name;
	private Set<TradeListEntity> tradeLists = new HashSet<>(); 

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Column(name = "email", length = 500, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	@Column(name = "name", length = 150, nullable = false, unique = false)
	public String getName() {
		return name;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "user__trade_list",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="trade_list_id"))
	public Set<TradeListEntity> getTradeLists() {
		return tradeLists;
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue
	public Integer getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTradeLists(Set<TradeListEntity> tradeLists) {
		this.tradeLists = tradeLists;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
