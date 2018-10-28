package common;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException() {
        super();
    }
    
    public WrongCredentialsException(Throwable rootCause) {
        super(rootCause);
    }
}
