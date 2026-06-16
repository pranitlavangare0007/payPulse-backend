package bank_services_app.controller;

import bank_services_app.Dto.response.AccountResponse;
import bank_services_app.Dto.response.BalanceResponse;
import bank_services_app.Dto.request.AccountRecord;
import bank_services_app.models.Customer;
import bank_services_app.services.AccountServices;
import bank_services_app.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountControllerUser {

    @Autowired
    CustomerServices customerServices;

    @Autowired
    AccountServices accountServices;

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> openAccount(@RequestBody AccountRecord accountRecord, Authentication authentication){

        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

    return ResponseEntity.status(HttpStatus.CREATED).body( accountServices.openAccount(accountRecord,customer));
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber , Authentication authentication){
        String username = authentication.getName();

        Customer customer = customerServices.getUser(username);

        return  ResponseEntity.ok(accountServices.getAccount(customer,accountNumber));
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String accountNumber , Authentication authentication){
        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

        return  ResponseEntity.ok(accountServices.getBalance(customer,accountNumber));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(Authentication authentication){
        String username = authentication.getName();
        Customer customer = customerServices.getUser(username);

        return  ResponseEntity.ok(accountServices.getAllAccount(customer));
    }






}
