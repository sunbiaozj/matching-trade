package matchingtrade.test.random;

import matchingtrade.service.json.TradeListJson;

public class TradeListRandom {
	
	public TradeListJson next() {
		RandomString randomGenerator = new RandomString();
		TradeListJson result = new TradeListJson();
		result.setName(randomGenerator.nextName());

		TradeItemRandom tradeItemRandom = new TradeItemRandom();
		result.getTradeItems().add(tradeItemRandom.next());
		result.getTradeItems().add(tradeItemRandom.next());
		result.getTradeItems().add(tradeItemRandom.next());
		
		return result;
	}

}
