package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.entity.Person;

@Component
public class PersonDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.save(person);
	}
	
	@Transactional
	public List<Person> search() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM Person");
		@SuppressWarnings("unchecked")
		List<Person> result = query.list();
		return result;
	}
}
