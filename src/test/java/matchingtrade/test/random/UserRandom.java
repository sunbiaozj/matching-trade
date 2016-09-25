package matchingtrade.test.random;

import matchingtrade.service.json.UserJson;

public class UserRandom {
	
	public UserJson next() {
		RandomString random = new RandomString();
		UserJson result = new UserJson();
		result.setName(random.nextName());
		result.setEmail(random.nextEmail());
		return result;
	}

}
