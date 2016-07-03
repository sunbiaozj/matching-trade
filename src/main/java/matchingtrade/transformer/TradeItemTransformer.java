package matchingtrade.transformer;

import org.springframework.beans.BeanUtils;

import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;

public class TradeItemTransformer {
	
	public TradeItemEntity transform(TradeItemJson json) {
		if (json == null) {
			return null;
		}
		TradeItemEntity result = new TradeItemEntity();
		BeanUtils.copyProperties(json, result);
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
