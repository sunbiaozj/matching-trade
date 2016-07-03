package matchingtrade.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;

public class TradeListTransformer {
	
	public TradeListEntity transform (TradeListJson json) {
		if (json == null) {
			return null;
		}
		
		TradeListEntity result = new TradeListEntity();
		BeanUtils.copyProperties(json, result, "tradeItems");
		
		if (!CollectionUtils.isEmpty(json.getTradeItems())) {
			TradeItemTransformer transformer = new TradeItemTransformer();
			for (TradeItemJson ti : json.getTradeItems()) {
				result.getTradeItems().add(transformer.transform(ti));
			}
		}
		
		return result;
	}

}
