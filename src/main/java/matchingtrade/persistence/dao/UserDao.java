package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
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

	@Transactional
	public UserEntity get(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(UserEntity.class);
		c.add(Restrictions.eq("email", email));
		UserEntity result = (UserEntity) c.uniqueResult();
		return result;
	}
	
	@Transactional
	public List<UserEntity> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM UserEntity");
		@SuppressWarnings("unchecked")
		List<UserEntity> result = query.list();
		return result;
	}
}
