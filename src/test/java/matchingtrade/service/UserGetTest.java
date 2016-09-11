package matchingtrade.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.TradeItemJson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class UserGetTest {
	
	@Autowired
	private UserDao userDao;

	@Test
	@Rollback(false)
	public void get() {
		
		List<UserEntity> allUsers = userDao.getAll();
		
		System.out.println(this.getClass().getSimpleName() + allUsers);
	}

}
