package bank_services_app.exceptionHandling;

public class InvalidDateRangeException extends BankingExceptions {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
