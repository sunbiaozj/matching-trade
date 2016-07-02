package matchingtrade.test.random;

import matchingtrade.service.json.TradeItemJson;

public class TradeItemRandom {
	
	public TradeItemJson get() {
		RandomString randomGenerator = new RandomString();
		TradeItemJson result = new TradeItemJson();
		result.setName(randomGenerator.nextName());
		result.setDescription(randomGenerator.nextDescription());
		return result;
	}

}
