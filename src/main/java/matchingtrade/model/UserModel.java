package matchingtrade.model;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class UserModel {
	
	@Autowired
	UserDao userDao;

	@Transactional
	public UserEntity get(Integer userId) {
    	UserEntity userEntity = userDao.get(userId);
    	return userEntity;
	}

	@Transactional
	public UserEntity put(UserEntity entity) {
		userDao.save(entity);
		return entity;
	}

}
