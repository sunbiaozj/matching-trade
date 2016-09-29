package matchingtrade.persistence.dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.entity.UserEntity;

@Component
public class UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(UserEntity userEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userEntity);
	}
	
	@Transactional
	public UserEntity get(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		return (UserEntity) session.get(UserEntity.class, userId);
	}

	public UserEntity get(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(UserEntity.class);
		c.add(Restrictions.eq("email", email));
		UserEntity result = (UserEntity) c.uniqueResult();
		return result;
	}
	
}
