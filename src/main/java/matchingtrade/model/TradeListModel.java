package matchingtrade.model;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.authentication.UserAuthentication;
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

	@Transactional
	public TradeListEntity save(UserAuthentication user, TradeListEntity entity) {
    	UserEntity userEntity = userDao.get(user.getUserId());
    	userEntity.getTradeLists().add(entity);
    	userDao.save(userEntity);
    	return entity;
	}

	@Transactional
	public TradeListEntity get(Integer tradeListId) {
		return tradeListDao.get(tradeListId);
	}

	public SearchResult<TradeListEntity> search(SearchCriteria sc) {
    	SearchResult<TradeListEntity> result = tradeListDao.search(sc);
    	return result;
	}
	
}
