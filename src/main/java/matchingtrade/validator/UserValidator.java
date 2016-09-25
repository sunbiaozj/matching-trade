package matchingtrade.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;

@Component
public class UserValidator {

	@Autowired
	private UserDao dao;

	public void validateNew(UserJson json) {
		EmailValidator validator = EmailValidator.getInstance();
		boolean isValid = validator.isValid(json.getEmail());
		if (!isValid) {
			throw new IllegalArgumentException(json.getEmail() + " is not an valid email.");
		}
	}
	
	public void validatePut(UserJson json) {
		validateNew(json);
		
		if (json.getUserId() == null) {
			throw new IllegalArgumentException("Cannot PUT a user without UserJson.userId.");
		}
		UserEntity userEntity = dao.get(json.getUserId());
		if (!userEntity.getEmail().equals(json.getEmail())) {
			throw new IllegalArgumentException("Cannot update UserJson.email on PUT operations.");
		}
	}

}
