package mtrade;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void save(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.save(person);
	}
}
