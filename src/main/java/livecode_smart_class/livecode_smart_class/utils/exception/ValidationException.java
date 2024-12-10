package livecode_smart_class.livecode_smart_class.utils.exception;

public class ValidationException extends RuntimeException {
    public ValidationException (String message, Throwable cause) {
        super(message, cause);
    }
}