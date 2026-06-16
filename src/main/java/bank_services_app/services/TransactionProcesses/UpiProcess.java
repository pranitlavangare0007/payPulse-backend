package bank_services_app.services.TransactionProcesses;

import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.exceptionHandling.AccountNotFoundException;
import bank_services_app.exceptionHandling.InvalidRequestException;
import bank_services_app.models.AccountDetails;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.repositry.AccountRepo;
import bank_services_app.services.CoreBanking.Core;
import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class UpiProcess implements TransactionProcesses{
    @Override
    public TransactionChannels channel() {
        return TransactionChannels.UPI;
    }

    private final Core core;
    private final AccountRepo accountRepo;
    public UpiProcess( Core core, AccountRepo accountRepo) {
        this.core = core;
        this.accountRepo = accountRepo;
    }
    @Override
    public TransactionalResponse process(String accountNumberSender, TransactionRequest request, Customer customer, TransactionType transactionType) {
        if(request.channel() == TransactionChannels.UPI && request.accountNumberReceiver() != null){
            throw new InvalidRequestException("Do not send account number for upi");
        }
   AccountDetails  receiver = accountRepo.findByUpiId(request.upiId()).orElseThrow( ()-> new AccountNotFoundException("Account not found"));
   AccountDetails sender = accountRepo.findByCustomerAndAccountNumber(customer,accountNumberSender);

     return core.executeTransfer(sender,receiver,request.amount(), request.mpin(),request.channel(),sender.getUpiId(),receiver.getUpiId());

    }
}
