package matchingtrade.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.common.Criterion;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.model.TradeItemModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.TradeItemJson;

@Component
public class TradeItemValidator extends Validator {

	@Autowired
	private TradeItemModel tradeItemModel;

	public void validatePut(Integer userId, Integer tradeItemId, TradeItemJson json) {
		// Validate if tradeItemId matches TradeItem.tradeItemId
		if (json.getTradeItemId() != null && !json.getTradeItemId().equals(tradeItemId)) {
			throw new IllegalArgumentException("TradeItem.tradeItemId: [" + json.getTradeItemId() + "] does not match the resource /tradeitems/" + tradeItemId);
		}
		
		// Validate if userId owns the resource
		SearchCriteria searchCriteria = new SearchCriteria(new Pagination(1,10));
		searchCriteria.addCriterion(new Criterion(UserEntity.Field.userId, userId));
		searchCriteria.addCriterion(new Criterion(TradeItemEntity.Field.tradeItemId, tradeItemId));
		SearchResult<TradeItemEntity> searchResult = tradeItemModel.search(searchCriteria);
		if (searchResult.getResultList().isEmpty()) {
			throw new IllegalArgumentException("User.userId does not have permission to edit TradeItem.tradeItemId:" + tradeItemId);
		}
	}
}
