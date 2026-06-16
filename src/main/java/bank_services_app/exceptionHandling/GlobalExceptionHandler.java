package bank_services_app.exceptionHandling;

import bank_services_app.Dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleAccountDeactivate(AccountDeactivatedException ex){
        return new ResponseEntity<>(
                new ErrorResponse(404, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccount(AccountNotFoundException ex){
        return new ResponseEntity<>(
                new ErrorResponse(404, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CustomerNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomer(CustomerNotExistsException ex){
        return new ResponseEntity<>(
                new ErrorResponse(404, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleBalance(InsufficientBalanceException ex){
        return new ResponseEntity<>(
                new ErrorResponse(400, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(AccountCreationLimitReachedException.class)
    public ResponseEntity<ErrorResponse> handleAccountCreation(AccountCreationLimitReachedException ex){
        return new ResponseEntity<>(
                new ErrorResponse(400, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidMpin.class)
    public ResponseEntity<ErrorResponse> handleMpin(InvalidMpin ex){
        return new ResponseEntity<>(
                new ErrorResponse(401, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleDate(InvalidDateRangeException ex){
        return new ResponseEntity<>(
                new ErrorResponse(400, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleRequest(InvalidRequestException ex){
        return new ResponseEntity<>(
                new ErrorResponse(401, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(InvalidTransactionChannelException.class)
    public ResponseEntity<ErrorResponse> handleTransactionChannel(InvalidTransactionChannelException ex){
        return new ResponseEntity<>(
                new ErrorResponse(401, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(RestrictedAccessException.class)
    public ResponseEntity<ErrorResponse> handleAdminRequest(RestrictedAccessException ex){
        return new ResponseEntity<>(
                new ErrorResponse(401, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<ErrorResponse> handleAmount(NegativeAmountException ex){
        return new ResponseEntity<>(
                new ErrorResponse(400, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(TransactionHistoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionHistory(TransactionHistoryNotFoundException ex){
        return new ResponseEntity<>(
                new ErrorResponse(404, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedAccessException ex){
        return new ResponseEntity<>(
                new ErrorResponse(403, ex.getMessage(), LocalDateTime.now()),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex){
        return new ResponseEntity<>(
                new ErrorResponse(500, "Something went wrong", LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
