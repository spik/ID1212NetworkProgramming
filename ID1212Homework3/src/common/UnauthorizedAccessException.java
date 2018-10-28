package common;

/**
 * This exception is thrown when a user tries to access a file it does not have access to 
 */
public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException() {
        super();
    }
    
    public UnauthorizedAccessException(Throwable rootCause) {
        super(rootCause);
    }
}
