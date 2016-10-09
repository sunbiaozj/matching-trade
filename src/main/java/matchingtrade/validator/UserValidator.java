package matchingtrade.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;

@Component
public class UserValidator extends Validator {

	@Autowired
	private UserModel userModel;

	public void validatePut(Integer userId, UserJson json) {
		if (json.getUserId() != null && !json.getUserId().equals(userId)) {
			throw new IllegalArgumentException("User.userId: [" + json.getUserId() + "] does not match the resource /users/" + userId);
		}
		
		UserEntity userEntity = userModel.get(userId);
		if (!userEntity.getEmail().equals(json.getEmail())) {
			throw new IllegalArgumentException("Cannot update User.email on PUT operations.");
		}
		
	}

}
