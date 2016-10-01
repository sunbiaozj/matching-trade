package matchingtrade.test.random;

import matchingtrade.service.json.UserJson;

public class UserRandom {
	
	public UserJson next() {
		StringRandom random = new StringRandom();
		UserJson result = new UserJson();
		result.setName(random.nextName());
		result.setEmail(random.nextEmail());
		return result;
	}
	
	public UserJson next(Integer userId) {
		UserJson result = next();
		result.setUserId(userId);
		return result;
	}

}
