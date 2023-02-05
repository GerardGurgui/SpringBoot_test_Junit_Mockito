package udemy.test_SpringBoot.app.exceptions;

public class InsufficentFundsException extends RuntimeException{

    public InsufficentFundsException(String message) {
        super(message);
    }

}
