package bank_services_app.services.TransactionProcesses;

import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;

public interface TransactionProcesses {
    TransactionChannels channel();

    TransactionalResponse process(String accountNumber, TransactionRequest request, Customer customer, TransactionType transactionType);
}
