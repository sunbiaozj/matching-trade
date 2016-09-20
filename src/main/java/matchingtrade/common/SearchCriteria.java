package matchingtrade.common;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

public class SearchCriteria {
	public enum OrderBy {
		ASC,DESC
	}
	private Pagination pagination;
	private List<Order> orderBy = new ArrayList<>();
	
	public void addOrderBy(Order order) {
		this.orderBy.add(order);
	}
	
	public List<Order> getOrderBy() {
		return orderBy;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public SearchCriteria(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setOrderBy(List<Order> orderBy) {
		this.orderBy = orderBy;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
