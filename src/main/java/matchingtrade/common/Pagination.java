package matchingtrade.common;

public class Pagination {
	private Integer limit;
	private Integer page;
	private Long total;

	public Pagination(Integer page, Integer limit, Long total) {
		this.page = page;
		this.limit = limit;
		this.total = total;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getPage() {
		return page;
	}

	public Long getTotal() {
		return total;
	}

}
