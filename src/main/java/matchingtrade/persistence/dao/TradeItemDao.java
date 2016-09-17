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
import matchingtrade.common.util.PersistanceUtil;
import matchingtrade.persistence.entity.TradeItemEntity;

@Component
public class TradeItemDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(TradeItemEntity tradeItemEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tradeItemEntity);
	}
	
	@Transactional
	public TradeItemEntity get(Integer tradeItemId) {
		Session session = sessionFactory.getCurrentSession();
		return (TradeItemEntity) session.get(TradeItemEntity.class, tradeItemId);
	}
	
	@Transactional
	public SearchResult<TradeItemEntity> get(SearchCriteria searchCriteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(TradeItemEntity.class);
		
		Long resultCount = PersistanceUtil.getRowCount(criteria);
		if (searchCriteria != null) {
			PersistanceUtil.applyPaginationToCriteria(searchCriteria.getPagination(), criteria);
		}
		
		@SuppressWarnings("unchecked")
		List<TradeItemEntity> resultList = criteria.list();

		Pagination resultPagination = new Pagination(
				searchCriteria.getPagination().getPage(),
				searchCriteria.getPagination().getLimit(),
				resultCount);
		SearchResult<TradeItemEntity> result = new SearchResult<TradeItemEntity>(resultList, resultPagination);
		return result;
	}
}
