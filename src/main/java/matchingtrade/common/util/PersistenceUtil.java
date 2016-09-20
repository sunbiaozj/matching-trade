package matchingtrade.common.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;

public class PersistenceUtil {
	
	/**
	 * Apply the order by from SearchCriteria to Criteria.
	 * @param searchCriteria
	 * @param criteria
	 */
	public static void applyOrderBy(SearchCriteria searchCriteria, Criteria criteria) {
		if (!searchCriteria.getOrderBy().isEmpty()) {
			for (Order o : searchCriteria.getOrderBy()) {
				criteria.addOrder(o);
			}
		}
	}

	private static void applyPaginationToCriteria(Pagination pagination, Criteria criteria) {
		if (pagination.getLimit() == null || pagination.getPage() == null) {
			return;
		}
		int firstResult = pagination.getLimit() * (pagination.getPage() - 1);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagination.getLimit());
	}
	
	/**
	 * Returns a new Pagination object based on Pagination and Criteria.
	 * Additionally, it will apply projections to determine the <i>rowCount</i>
	 * which will be set to <i>Pagination.total</i>. Therefore, do not use this
	 * method if you need to preserve any projections already assigned to the
	 * <i>Criteria</i> passed as parameter.
	 * 
	 * @param searchCriteria
	 * @param criteria
	 * @return Pagination loaded from the persistence layer
	 */
	public static Pagination getPagination(Pagination pagination, Criteria criteria) {
		Long resultCount = getRowCount(criteria);
		if (pagination != null) {
			applyPaginationToCriteria(pagination, criteria);
		}
		Pagination resultPagination = new Pagination(pagination.getPage(), pagination.getLimit(), resultCount);
		return resultPagination;
	}

	/**
	 * Return the row count for the criteria. <b>Warning</b>: This method will
	 * clear the current criteria projection.

	 * @param criteria
	 */
	private static Long getRowCount(Criteria criteria) {
		criteria.setProjection(Projections.rowCount());
		Long result = (Long) criteria.uniqueResult();
		criteria.setProjection(null);
		return result;
	}
}
