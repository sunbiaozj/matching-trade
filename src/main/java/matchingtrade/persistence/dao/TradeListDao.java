package matchingtrade.persistence.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.entity.TradeListEntity;

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
	public TradeListEntity get(Integer tradeListId) {
		Session session = sessionFactory.getCurrentSession();
		return (TradeListEntity) session.get(TradeListEntity.class, tradeListId);
	}
}
