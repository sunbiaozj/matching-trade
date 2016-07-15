package matchingtrade.transformer;

import org.springframework.beans.BeanUtils;

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
		return result;
	}
	
	public TradeItemJson transform(TradeItemEntity entity) {
		if (entity == null) {
			return null;
		}
		TradeItemJson result = new TradeItemJson();
		BeanUtils.copyProperties(entity, result);
		return result;
	}

}
