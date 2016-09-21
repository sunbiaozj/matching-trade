package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.PersistenceUtil;
import matchingtrade.persistence.entity.TradeItemEntity;

@Component
public class TradeItemDao extends Dao<TradeItemEntity> {

	@Override
	protected Class getEntityClass() {
		return TradeItemEntity.class;
	}

	@Transactional
	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(TradeItemEntity.class);

		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), criteria);
		PersistenceUtil.applyOrderBy(searchCriteria, criteria);

		List<TradeItemEntity> resultList = criteria.list();

		SearchResult<TradeItemEntity> result = new SearchResult<>(resultList, resultPagination);
		return result;
	}

}
