package bank_services_app.exceptionHandling;

public class InvalidTransactionChannelException extends RuntimeException {
    public InvalidTransactionChannelException(String message) {
        super(message);
    }
}
