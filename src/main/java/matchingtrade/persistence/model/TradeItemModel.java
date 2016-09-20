package matchingtrade.persistence.model;

import java.util.Date;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;

@Component
public class TradeItemModel {
	
	@Autowired
	TradeItemDao tradeItemDao;

	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		// By default results are sorted by updatedDateTime
    	searchCriteria.addOrderBy(Order.desc(TradeItemEntity.Fields.updatedDateTime.toString()));
    	SearchResult<TradeItemEntity> result = tradeItemDao.search(searchCriteria);
    	return result;
	}
	
	public void save(TradeItemEntity entity) {
    	entity.setUpdatedDateTime(new Date());
		tradeItemDao.save(entity);
	}

	public TradeItemEntity get(Integer tradeItemId) {
		return tradeItemDao.get(tradeItemId);
	}

}
