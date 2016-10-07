package matchingtrade.test.random;

import matchingtrade.service.json.TradeListJson;

public class TradeListRandom {
	
	public TradeListJson next() {
		StringRandom randomGenerator = new StringRandom();
		TradeListJson result = new TradeListJson();
		result.setName(randomGenerator.nextName());
		return result;
	}
	
	public TradeListJson next(Integer tradeListId) {
		TradeListJson result = next();
		result.setTradeListId(tradeListId);
		return result;
	}

}
