package matchingtrade.common;

public class SearchCriteria {
	private Pagination pagination;
	
	public SearchCriteria(Pagination pagination) {
		this.pagination = pagination;
	}

	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
