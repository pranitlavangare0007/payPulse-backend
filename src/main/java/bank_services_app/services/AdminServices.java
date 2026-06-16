package bank_services_app.services;

import bank_services_app.Dto.response.AccountResponse;
import bank_services_app.exceptionHandling.RestrictedAccessException;
import bank_services_app.models.AccountDetails;
import bank_services_app.models.Customer;
import bank_services_app.repositry.AccountRepo;
import bank_services_app.util.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    private final AccountRepo accountRepo;
    public AdminServices(AccountRepo accountRepo){
        this.accountRepo=accountRepo;

    }

    public List<AccountResponse> getAllAccounts(Customer customer) {

        if(customer.getRole() != Role.ADMIN) throw new RestrictedAccessException("Access denied");

       List<AccountDetails> details = accountRepo.findAll();
     return   details.stream().map(account ->
               new AccountResponse(
                       account.getAccountNumber(),
                       account.getAccountType(),
                       account.getAccountStatus(),
                       account.getAccountPurpose(),
                       account.getBalance(),
                       account.getUpiId()

               )
               ).toList();
    }
}
