package matchingtrade.validator;

/**
 * @author rafael.santos.bra@gmail.com
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -8260859911615409990L;

	public enum Type {
        MANDATORY_PARAMETER_MISSING, INVALID_PARAMETER
    }

    public ValidationException(Type exceptionType, String exceptionDetails) {
        super(exceptionType.toString() + ": " + exceptionDetails);
    }

}
