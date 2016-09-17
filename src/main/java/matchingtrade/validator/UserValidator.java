package matchingtrade.validator;

import matchingtrade.common.util.Util;
import matchingtrade.service.json.UserJson;

public class UserValidator {
	
	public void validatePost(UserJson json) {
		if (json == null) {
			throw new NullPointerException("Cannot POST a user using an empty UserJson");
		}
		if (Util.isEmpty(json.getName().trim())) {
			throw new IllegalArgumentException("Cannot POST a user with empty UserJson.name");
		}
	}

	public void validatePut(UserJson json) {
		validatePost(json);
		if (json.getUserId() == null) {
			throw new IllegalArgumentException("Cannot PUT a user without UserJson.userId");
		}
	}

}
