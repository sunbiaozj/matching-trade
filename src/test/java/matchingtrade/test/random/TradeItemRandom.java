package matchingtrade.test.random;

import java.util.Date;

import matchingtrade.service.json.TradeItemJson;

public class TradeItemRandom {
	
	public TradeItemJson next() {
		RandomString randomGenerator = new RandomString();
		TradeItemJson result = new TradeItemJson();
		result.setName(randomGenerator.nextName());
		result.setDescription(randomGenerator.nextDescription());
		result.setUpdatedDateTime(new Date());
		return result;
	}

}
