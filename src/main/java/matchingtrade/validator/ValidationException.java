package matchingtrade.validator;

/**
 * @author rafael.santos.bra@gmail.com
 */
public class ValidationException extends RuntimeException {

    public enum Type {
        MANDATORY_PARAMETER_MISSING, INVALID_PARAMETER
    }

    public ValidationException(Type exceptionType, String exceptionDetails) {
        super(exceptionType.toString() + ": " + exceptionDetails);
    }

}
