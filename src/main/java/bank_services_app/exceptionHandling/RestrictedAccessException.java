package bank_services_app.exceptionHandling;

public class RestrictedAccessException extends BankingExceptions {
    public RestrictedAccessException(String message) {
        super(message);
    }
}
