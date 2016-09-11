package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import matchingtrade.persistence.entity.TradeListEntity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeListDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(TradeListEntity tradeListEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tradeListEntity);
	}
	
	@Transactional
	public List<TradeListEntity> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM TradeListEntity");
		@SuppressWarnings("unchecked")
		List<TradeListEntity> result = query.list();
		return result;
	}

	@Transactional
	public TradeListEntity get(Integer tradeListId) {
		Session session = sessionFactory.getCurrentSession();
		return (TradeListEntity) session.get(TradeListEntity.class, tradeListId);
	}
}
