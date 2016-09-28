package matchingtrade.validator;

import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.service.json.UserJson;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeItemValidator {

	public void validateSearch(Integer _page, Integer _limit) {
		if (_page != null && _page < 0) {
			throw new ValidationException(ValidationException.Type.INVALID_PARAMETER, "_page must be greater than 0.");
		}
		if (_limit != null && _limit < 0) {
			throw new ValidationException(ValidationException.Type.INVALID_PARAMETER, "_limit must be greater than 0.");
		}
	}
}
