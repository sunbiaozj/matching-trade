package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.SearchCriteria;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.util.PersistanceUtil;

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
	public List<TradeItemEntity> get(SearchCriteria searchCriteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(TradeItemEntity.class);
		
		if (searchCriteria != null) {
			PersistanceUtil.applyPaginationToCriteria(searchCriteria.getPagination(), cr);
		}

		@SuppressWarnings("unchecked")
		List<TradeItemEntity> result = cr.list();
		return result;
	}
}
