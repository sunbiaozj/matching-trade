package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.entity.TradeItemEntity;

@Component
public class TradeItemDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(TradeItemEntity tradeItemEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(tradeItemEntity);
	}
	
	@Transactional
	public TradeItemEntity get(Integer tradeItemId) {
		Session session = sessionFactory.getCurrentSession();
		return (TradeItemEntity) session.get(TradeItemEntity.class, tradeItemId);
	}
	
	@Transactional
	public List<TradeItemEntity> search() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM TradeItemEntity");
		@SuppressWarnings("unchecked")
		List<TradeItemEntity> result = query.list();
		return result;
	}
}
