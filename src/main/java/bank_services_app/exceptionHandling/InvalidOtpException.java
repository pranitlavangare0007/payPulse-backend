package bank_services_app.exceptionHandling;

public class InvalidOtpException extends BankingExceptions {
    public InvalidOtpException(String message) {
        super(message);
    }
}
