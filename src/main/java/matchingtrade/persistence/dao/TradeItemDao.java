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
	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(TradeItemEntity.class);
		
		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), criteria);
		PersistenceUtil.applyOrderBy(searchCriteria, criteria);
		
		@SuppressWarnings("unchecked")
		List<TradeItemEntity> resultList = criteria.list();

		SearchResult<TradeItemEntity> result = new SearchResult<TradeItemEntity>(resultList, resultPagination);
		return result;
	}

}
