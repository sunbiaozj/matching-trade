package matchingtrade.test.random;

import matchingtrade.service.json.TradeListJson;

public class TradeListRandom {
	
	public TradeListJson next() {
		StringRandom randomGenerator = new StringRandom();
		TradeListJson result = new TradeListJson();
		result.setName(randomGenerator.nextName());

		TradeItemRandom tradeItemRandom = new TradeItemRandom();
		result.getTradeItems().add(tradeItemRandom.next());
		result.getTradeItems().add(tradeItemRandom.next());
		result.getTradeItems().add(tradeItemRandom.next());
		
		return result;
	}
	
	public TradeListJson next(Integer tradeListId) {
		TradeListJson result = next();
		result.setTradeListId(tradeListId);
		return result;
	}

}
