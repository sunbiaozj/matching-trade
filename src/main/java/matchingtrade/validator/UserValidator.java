package matchingtrade.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;

@Component
public class UserValidator extends Validator {

	@Autowired
	private UserDao dao;

	public void validatePut(UserJson json) {
		UserEntity userEntity = dao.get(json.getUserId());
		if (userEntity == null) {
			throw new IllegalArgumentException("Cannot update user with UserJson.userId: ["+json.getUserId()+"]. User does not exist.");
		}
		if (!userEntity.getEmail().equals(json.getEmail())) {
			throw new IllegalArgumentException("Cannot update UserJson.email on PUT operations.");
		}
		
	}

}
