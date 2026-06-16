package bank_services_app.controller;

import bank_services_app.Dto.response.StatementResult;
import bank_services_app.Dto.response.TransactionResult;
import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.Dto.request.StatementRequest;
import bank_services_app.services.Router;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.services.CustomerServices;
import bank_services_app.services.TransactionServices;
import bank_services_app.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    CustomerServices customerServices;

    @Autowired
    TransactionServices transactionServices;

    @Autowired
    Router router ;

    @PatchMapping("/accounts/{accountNumber}/deposit")
    public ResponseEntity<TransactionalResponse> deposit(@PathVariable String accountNumber, @RequestBody TransactionRequest request, Authentication authentication) {

        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

        TransactionType transactionType = TransactionType.CREDIT;

        return  ResponseEntity.ok(router.route(accountNumber, request, customer, transactionType));
    }

    @PatchMapping("/accounts/{accountNumber}/withdraw")
    public ResponseEntity<TransactionalResponse> withdraw(@PathVariable String accountNumber, @RequestBody TransactionRequest request, Authentication authentication) {

        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

        TransactionType transactionType = TransactionType.DEBIT;

        return  ResponseEntity.ok(router.route(accountNumber, request, customer, transactionType));
    }

    @PatchMapping("/accounts/{accountNumber}/transfer")
    public ResponseEntity<TransactionalResponse> transfer(@PathVariable String accountNumber, @RequestBody TransactionRequest request, Authentication authentication) {

        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

        TransactionType transactionType = TransactionType.DEBIT;

        return  ResponseEntity.ok(router.route(accountNumber, request, customer, transactionType));
    }

    @PostMapping("/accounts/{accountNumber}/statement")
    public ResponseEntity<StatementResult> getStatement(@PathVariable String accountNumber , @RequestBody StatementRequest request , Authentication authentication){

        String username = authentication.getName();

        return ResponseEntity.ok(transactionServices.getStatement(accountNumber,request ,username));
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<TransactionResult> getTransactions(@PathVariable String accountNumber , Authentication authentication){

        String username = authentication.getName();

        return ResponseEntity.ok(transactionServices.getTransactions(accountNumber ,username));
    }
}
