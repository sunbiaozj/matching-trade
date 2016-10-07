package matchingtrade.transformer;

import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.TradeListJson;

public class TradeListTransformer {
	
	public TradeListEntity transform (TradeListJson json) {
		if (json == null) {
			return null;
		}
		
		TradeListEntity result = new TradeListEntity();
		result.setName(json.getName());
		result.setTradeListId(json.getTradeListId());
		
		return result;
	}

	public TradeListJson transform(TradeListEntity entity) {
		if (entity == null) {
			return null;
		}
		TradeListJson result = new TradeListJson();
		result.setName(entity.getName());
		result.setTradeListId(entity.getTradeListId());
		return result;
	}

}
