package bank_services_app.exceptionHandling;

public class UnauthorizedAccessException extends BankingExceptions {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
