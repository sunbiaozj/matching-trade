package mtrade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import matchingtrade.persistence.dao.PersonDao;
import matchingtrade.persistence.entity.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
@TransactionConfiguration(defaultRollback=false)
public class PersonTest {
	
	@Autowired
	PersonDao personDao;
	
	@Test
	@Rollback(false)
	public void savePerson() {
		Person person = new Person();
		person.setName(new RandomNameGenerator().get());
		personDao.save(person);
	}

}
