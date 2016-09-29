package matchingtrade.validator;

import org.springframework.stereotype.Component;

import matchingtrade.validator.ValidationException.Type;

@Component
public class Validator {

	public void validatePagination(Integer _page, Integer _limit) {
		if (_page != null && _page <= 0) {
			throw new ValidationException(Type.INVALID_PARAMETER, "_page must be greater than 0.");
		}
		if (_limit != null && _limit <= 0) {
			throw new ValidationException(Type.INVALID_PARAMETER, "_limit must be greater than 0.");
		}
	}

}
