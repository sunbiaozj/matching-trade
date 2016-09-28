package matchingtrade.authorization;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.authentication.UserAuthentication;
import matchingtrade.model.TradeItemModel;
import matchingtrade.model.UserModel;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class TradeItemAuthorization extends Authorization {
	
	@Autowired
	private TradeItemModel model;
	
	@Autowired
	private UserModel userModel;
	
	@Transactional
	public void verifyIfUserIsAuthorizedOverTheResource(UserAuthentication userAuthentication, Integer tradeItemId) {
		TradeItemEntity entity = model.get(tradeItemId);
		UserEntity userEntity = userModel.get(userAuthentication.getUserId());
		// TODO: Improve performance here
		boolean contains = false;
		for (TradeListEntity tl: userEntity.getTradeLists()) {
			if (tl.getTradeItems().contains(entity)) {
				contains = true;
			}
		}
		if (!contains) {
			new AuthorizationException(AuthorizationException.Type.USER_NOT_AUTHORIZED);
		}
	}
}
