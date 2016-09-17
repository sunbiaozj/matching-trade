package matchingtrade.common;

public class SearchCriteria {
	private Pagination pagination;
	
	public SearchCriteria(Integer _page, Integer _limit) {
    	pagination = new Pagination(_page, _limit, null);
	}

	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
