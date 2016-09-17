package matchingtrade.common.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import matchingtrade.common.Pagination;

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
	
	/**
	 * Return the row count for the criteria.
	 * <b>Warning</b>: This method will clear the current criteria projection.
	 * @param criteria
	 */
	public static Long getRowCount(Criteria criteria) {
		criteria.setProjection(Projections.rowCount());
		Long result = (Long) criteria.uniqueResult();
		criteria.setProjection(null);
		return result;
	}
}
