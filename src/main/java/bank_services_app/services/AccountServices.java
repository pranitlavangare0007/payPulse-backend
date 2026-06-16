package bank_services_app.services;

import bank_services_app.Dto.response.AccountResponse;
import bank_services_app.Dto.response.BalanceResponse;
import bank_services_app.exceptionHandling.*;
import bank_services_app.models.AccountDetails;
import bank_services_app.Dto.request.AccountRecord;
import bank_services_app.models.Customer;
import bank_services_app.repositry.AccountRepo;
import bank_services_app.repositry.TransactionRepo;
import bank_services_app.util.AccountStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AccountServices {

private final AccountRepo accountRepo;
private final EmailService emailService;
public AccountServices(AccountRepo accountRepo , EmailService emailService) {
        this.accountRepo = accountRepo;
        this.emailService=emailService;
}
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String generateAccountNumber(AccountDetails accountDetails) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String time = timestamp.format(formatter);

    return "AC"+time;
    }
    public void verifyAccount(AccountDetails accountDetails){
        if(accountDetails==null){
            throw new AccountNotFoundException("Account not found");
        }
    }
    public void verifyAccountStatus(AccountStatus accountStatus){
        if(accountStatus == AccountStatus.DEACTIVATE){
            throw new AccountDeactivatedException("Account is deactivated");
        }
    }
    public void verifyMpinFormat(String mpin){
        if (mpin == null || !mpin.matches("\\d{6}")) {
            throw new InvalidMpin("MPIN must be 6 digits");
        }
    }
    public AccountResponse openAccount(AccountRecord accountRecord, Customer customer) {
        Long count = accountRepo.countByCustomer(customer);
        if(count>=3) throw new AccountCreationLimitReachedException(" Reached account Creation Limit");

        AccountDetails accountDetails = new AccountDetails();

        verifyMpinFormat(accountRecord.mpin());

        accountDetails.setAccountType(accountRecord.accountType());
        accountDetails.setMpin(encoder.encode(accountRecord.mpin()));
        accountDetails.setCustomer(customer);
        accountDetails.setAccountPurpose(accountRecord.accountPurpose());
        accountDetails.setAccountNumber(generateAccountNumber(accountDetails));
        accountDetails.setUpiId(customer.getPhoneNumber()+"."+accountDetails.getAccountNumber() .substring(accountDetails.getAccountNumber().length() -4)+"@payPulse");

        accountDetails =  accountRepo.save(accountDetails);

emailService.sendAccountDetailsEmail(customer.getEmail(), accountDetails,customer.getRole());
          return new AccountResponse(
                accountDetails.getAccountNumber(),
                accountDetails.getAccountType(),
                accountDetails.getAccountStatus(),
                accountDetails.getAccountPurpose(),
                accountDetails.getBalance(),
                  accountDetails.getUpiId()
        );
    }
    public AccountResponse getAccount(Customer customer,String accountNumber) {

   AccountDetails accountDetails =accountRepo.findByCustomerAndAccountNumber(customer,accountNumber);
   verifyAccount(accountDetails);

        return new AccountResponse(
                accountDetails.getAccountNumber(),
                accountDetails.getAccountType(),
                accountDetails.getAccountStatus(),
                accountDetails.getAccountPurpose(),
                accountDetails.getBalance(),
                accountDetails.getUpiId()

        );
    }
    public List<AccountResponse> getAllAccount(Customer customer) {
        List<AccountDetails> accounts =accountRepo.findAllByCustomer(customer);

        return accounts.stream()
                .map(accountDetails-> new AccountResponse(
                        accountDetails.getAccountNumber(),
                        accountDetails.getAccountType(),
                        accountDetails.getAccountStatus(),
                        accountDetails.getAccountPurpose(),
                        accountDetails.getBalance(),
                        accountDetails.getUpiId()

                ))
                .toList();
    }


    public BalanceResponse getBalance(Customer customer, String accountNumber) {
        AccountDetails accountDetails =accountRepo.findByCustomerAndAccountNumber(customer,accountNumber);

        verifyAccount(accountDetails);
        verifyAccountStatus(accountDetails.getAccountStatus());
        return new BalanceResponse(
                accountDetails.getAccountNumber(),
                accountDetails.getBalance());
    }

}
