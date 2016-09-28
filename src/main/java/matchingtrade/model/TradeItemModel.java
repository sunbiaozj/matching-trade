package matchingtrade.model;

import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.dao.TradeListDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;

@Component
public class TradeItemModel {
	
	@Autowired
	private TradeListDao tradeListDao;
	@Autowired
	private TradeItemDao tradeItemDao;

	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		// By default results are sorted by updatedDateTime
		if (searchCriteria.getOrderBy().isEmpty()) {
			//TODO Refactor SearchCriteria.Order
			searchCriteria.addOrderBy(Order.desc("ti."+TradeItemEntity.Field.updatedDateTime.toString()));
			System.out.println(TradeItemEntity.Field.updatedDateTime.getClass().getSimpleName());
			System.out.println(TradeItemEntity.Field.updatedDateTime.getClass().getName());
			System.out.println(TradeItemEntity.Field.updatedDateTime.getClass().getDeclaringClass());
			System.out.println(TradeItemEntity.Field.updatedDateTime.getClass().getDeclaringClass().getSimpleName());
			System.out.println(TradeItemEntity.Field.updatedDateTime.getClass().getDeclaringClass().getCanonicalName());
		}
    	SearchResult<TradeItemEntity> result = tradeItemDao.search(searchCriteria);
    	return result;
	}
	
	public void save(TradeItemEntity entity) {
    	entity.setUpdatedDateTime(new Date());
		tradeItemDao.save(entity);
	}

	@Transactional
	public TradeItemEntity save(Integer tradeListId, TradeItemEntity entity) {
		TradeListEntity tradeListEntity = tradeListDao.get(tradeListId);
		entity.setUpdatedDateTime(new Date());
		tradeListEntity.getTradeItems().add(entity);
		tradeListDao.save(tradeListEntity);
		return entity;
	}

	public TradeItemEntity get(Integer tradeItemId) {
		return tradeItemDao.get(tradeItemId);
	}

}
