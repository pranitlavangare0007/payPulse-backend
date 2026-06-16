package bank_services_app.services.TransactionProcesses;

import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.services.CoreBanking.Core;
import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CashProcess implements TransactionProcesses{

    @Override
    public TransactionChannels channel() {
        return TransactionChannels.CASH;
    }
    private final Core core;
    public CashProcess( Core core) {
       this.core = core;
    }
    @Transactional
    @Override
    public TransactionalResponse process(String accountNumber, TransactionRequest request, Customer customer, TransactionType transactionType) {

      return core.executeSingle(accountNumber,request,customer,transactionType,TransactionChannels.CASH);

    }
}
