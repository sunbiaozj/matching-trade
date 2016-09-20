package matchingtrade.transformer;

import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;

public class TradeItemTransformer {


	public TradeItemEntity transform(TradeItemJson json) {
		return transform(json, null);
	}
	
	public TradeItemEntity transform(TradeItemJson json, TradeItemEntity entity) {
		if (json == null) {
			return null;
		}
		
		TradeItemEntity result;
		if (entity != null) {
			result = entity;
		} else {
			result = new TradeItemEntity();
		}
		
		result.setDescription(json.getDescription());
		result.setName(json.getName());
		result.setTradeItemId(json.getTradeItemId());
		result.setUpdatedDateTime(json.getUpdatedDateTime());
		return result;
	}
	
	public TradeItemJson transform(TradeItemEntity entity) {
		if (entity == null) {
			return null;
		}
		TradeItemJson result = new TradeItemJson();
		result.setDescription(entity.getDescription());
		result.setName(entity.getName());
		result.setTradeItemId(entity.getTradeItemId());
		result.setUpdatedDateTime(entity.getUpdatedDateTime());
		return result;
	}

}
