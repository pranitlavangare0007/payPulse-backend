package bank_services_app.services;

import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.exceptionHandling.InvalidTransactionChannelException;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.services.TransactionProcesses.*;
import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Router {

    private final Map<TransactionChannels, TransactionProcesses> processormap;

    public Router(List<TransactionProcesses> processors) {
        this.processormap = processors.stream()
                .collect(Collectors.toMap(
                        TransactionProcesses::channel,
                        p -> p
                ));
    }
    public TransactionalResponse route(String accountNumber, TransactionRequest request, Customer customer , TransactionType transactionType) {

        if(request.channel() == null)  throw new InvalidTransactionChannelException(" channel cannot be null");
        if(!EnumSet.allOf(TransactionChannels.class).contains(request.channel())) throw new InvalidTransactionChannelException(" channel is not valid");

        TransactionChannels channel = request.channel();
        TransactionProcesses transactionProcesses;

        transactionProcesses= processormap.get(channel);
      return transactionProcesses.process(accountNumber,request,customer,transactionType);


    }
}
