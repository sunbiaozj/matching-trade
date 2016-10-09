package matchingtrade.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.persistence.dao.TradeListDao;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class TradeListModel {
	
	@Autowired
	TradeListDao tradeListDao;
	@Autowired
	UserDao userDao;

	public TradeListEntity get(Integer tradeListId) {
		return tradeListDao.get(tradeListId);
	}

	@Transactional
	public void save(Integer userId, TradeListEntity entity) {
    	UserEntity userEntity = userDao.get(userId);
    	userEntity.getTradeLists().add(entity);
    	userDao.save(userEntity);
	}
	
	public void save(TradeListEntity tradeListEntity) {
		tradeListDao.save(tradeListEntity);
	}
	
	public SearchResult<TradeListEntity> search(SearchCriteria sc) {
    	SearchResult<TradeListEntity> result = tradeListDao.search(sc);
    	return result;
	}
	
}
