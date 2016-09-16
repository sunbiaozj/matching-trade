package matchingtrade.util;

import org.hibernate.Criteria;

import matchingtrade.persistence.Pagination;

public class PersistanceUtil {

	public static void applyPaginationToCriteria(Pagination pagination, Criteria criteria) {
		// Account for pagination only if page>0 and limit>0
		if (pagination != null
				&& pagination.getPage() != null
				&& pagination.getPage() > 0
				&& pagination.getLimit() != null
				&& pagination.getLimit() > 0) {

			int firstResult = pagination.getLimit() * (pagination.getPage()-1);
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(pagination.getLimit());
		}
	}
}
