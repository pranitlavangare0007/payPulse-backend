package bank_services_app.services;

import bank_services_app.Dto.response.StatementResponse;
import bank_services_app.Dto.response.StatementResult;
import bank_services_app.Dto.response.TransactionResult;
import bank_services_app.exceptionHandling.AccountNotFoundException;
import bank_services_app.exceptionHandling.CustomerNotExistsException;
import bank_services_app.exceptionHandling.InvalidDateRangeException;
import bank_services_app.exceptionHandling.UnauthorizedAccessException;
import bank_services_app.models.AccountDetails;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.StatementRequest;
import bank_services_app.repositry.AccountRepo;
import bank_services_app.repositry.CustomerRepo;
import bank_services_app.repositry.TransactionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionServices {



    final private TransactionRepo transactionRepo;
   final private CustomerRepo  customerRepo;
   final private AccountRepo accountRepo;
   final private MpinService mpinService;

    public TransactionServices(TransactionRepo transactionRepo, CustomerRepo customerRepo, AccountRepo accountRepo , MpinService mpinService) {
        this.transactionRepo = transactionRepo;
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
        this.mpinService = mpinService;
    }



    public StatementResult getStatement(String accountNumber,StatementRequest request ,String username) {


        if(request.toDate().isBefore(request.fromDate()))
            throw new InvalidDateRangeException("Invalid date range");
        long days = ChronoUnit.DAYS.between(request.fromDate(), request.toDate());

        if(days < 0)
            throw new InvalidDateRangeException("Invalid date range");

        if(days > 365)
            throw new InvalidDateRangeException("Maximum statement period is 1 year");

        Customer customer = customerRepo.findByUsername(username);
               if(customer == null) throw new CustomerNotExistsException("Customer Not Found");

        AccountDetails account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(!account.getCustomer().getCustomerId().equals(customer.getCustomerId())){
            throw new UnauthorizedAccessException("Account not found");
        }


        LocalDateTime start = request.fromDate().atStartOfDay();

        LocalDateTime end = request.toDate().atTime(LocalTime.MAX);

        List<StatementResponse> transactions = transactionRepo
                .findByAccount_AccountNumberAndTransactionTimeBetweenOrderByTransactionTimeDesc(
                        accountNumber, start, end
                )
                .stream()
                .map(tx -> new StatementResponse(
                        tx.getReferenceNumber(),
                        tx.getCredit(),
                        tx.getDebit(),
                        tx.getBalance(),
                        tx.getDetails(),
                        tx.getTransactionType().name(),
                        tx.getTransactionChannels().name(),
                        tx.getTransactionTime()
                ))
                .toList();

        return new StatementResult(accountNumber,request.fromDate(),request.toDate(), transactions);
    }

    public TransactionResult getTransactions(String accountNumber,String username) {



        Customer customer = customerRepo.findByUsername(username);
        if(customer == null) throw new CustomerNotExistsException("Customer Not Found");

        AccountDetails account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(!account.getCustomer().getCustomerId().equals(customer.getCustomerId())){
            throw new UnauthorizedAccessException("Account not found");
        }




        List<StatementResponse> transactions = transactionRepo
                .findTop20ByAccount_AccountNumberOrderByTransactionTimeDesc(
                        accountNumber
                )
                .stream()
                .map(tx -> new StatementResponse(
                        tx.getReferenceNumber(),
                        tx.getCredit(),
                        tx.getDebit(),
                        tx.getBalance(),
                        tx.getDetails(),
                        tx.getTransactionType().name(),
                        tx.getTransactionChannels().name(),
                        tx.getTransactionTime()
                ))
                .toList();

        return new TransactionResult(accountNumber, transactions);
    }
}
