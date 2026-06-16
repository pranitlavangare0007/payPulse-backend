package bank_services_app.exceptionHandling;

public class AccountCreationLimitReachedException extends BankingExceptions {
    public AccountCreationLimitReachedException(String message) {
        super(message);
    }
}
