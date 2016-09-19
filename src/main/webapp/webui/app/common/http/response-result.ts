export class ResponseResult<T> {
	public pagination: Pagination;
	public data: T;

	public static transformPagination(paginationFromResponse: any): Pagination {
		let result = new Pagination();
		result.limit = paginationFromResponse.limit;
		result.page = paginationFromResponse.page;
		result.total = paginationFromResponse.total;
		return result;
	}
}

export class Pagination {
	public limit: number;
	public page: number;
	public total: number;

	public getPages(): Page[] {
		let result: Page[] = [];
		let totalPages = Math.ceil(this.total/this.limit);
		for (let i = 1; i <= totalPages; i++) {
			let p = new Page();
			p.number = i;
			p.limit = this.limit;
			result.push(p);
		}
		return result;
	}
}

export class Page {
	public number: number;
	public limit: number;
}