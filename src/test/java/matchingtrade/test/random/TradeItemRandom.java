package matchingtrade.test.random;

import java.util.Date;

import matchingtrade.service.json.TradeItemJson;

public class TradeItemRandom {
	
	public TradeItemJson next() {
		StringRandom randomGenerator = new StringRandom();
		TradeItemJson result = new TradeItemJson();
		result.setName(randomGenerator.nextName());
		result.setDescription(randomGenerator.nextDescription());
		result.setUpdatedDateTime(new Date());
		return result;
	}
	
	public TradeItemJson next(Integer tradeItemId) {
		TradeItemJson result = next();
		result.setTradeItemId(tradeItemId);
		return result;
	}

}
