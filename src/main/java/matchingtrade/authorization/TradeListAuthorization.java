package matchingtrade.authorization;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.model.TradeListModel;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class TradeListAuthorization extends Authorization {
	
	@Autowired
	private TradeListModel model;
	
	@Autowired
	private UserModel userModel;
	
	@Transactional
	public void authorizeGet(UserAuthentication userAuthentication, Integer tradeListId) {
		TradeListEntity entity = model.get(tradeListId);
		UserEntity userEntity = userModel.get(userAuthentication.getUserId());
		// TODO: Improve performance here
		if (!userEntity.getTradeLists().contains(entity)) {
			new AuthorizationException(AuthorizationException.Type.USER_NOT_AUTHORIZED);
		}
	}
}
