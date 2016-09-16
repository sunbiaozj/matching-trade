package matchingtrade.persistence;

public class SearchCriteria {
	private Pagination pagination;
	
	public SearchCriteria(Integer _page, Integer _limit) {
    	pagination = new Pagination();
    	pagination.setLimit(_limit);
    	pagination.setPage(_page);
	}

	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
