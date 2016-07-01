package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.entity.TradeItem;

@Component
public class PersonDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(TradeItem tradeItem) {
		Session session = sessionFactory.getCurrentSession();
		session.save(tradeItem);
	}
	
	@Transactional
	public List<TradeItem> search() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM TradeItem");
		@SuppressWarnings("unchecked")
		List<TradeItem> result = query.list();
		return result;
	}
}
