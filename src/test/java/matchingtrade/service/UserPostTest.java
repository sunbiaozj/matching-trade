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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class UserPostTest {
	
	@Autowired
	private UserDao userDao;

	@Test
	@Rollback(false)
	public void post() {
		UserEntity u = new UserEntity();
		u.setEmail(System.currentTimeMillis() + "@integrationtest.com");
		u.setName(System.currentTimeMillis() + "-integrationtest.com");
		
		userDao.save(u);
		
		
		List<UserEntity> allUsers = userDao.getAll();
		
		System.out.println(this.getClass().getSimpleName() + allUsers);
	}

}
