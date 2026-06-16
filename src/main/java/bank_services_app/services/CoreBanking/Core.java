package bank_services_app.services.CoreBanking;

import bank_services_app.Dto.response.TransactionalResponse;
import bank_services_app.exceptionHandling.AccountDeactivatedException;
import bank_services_app.exceptionHandling.AccountNotFoundException;
import bank_services_app.exceptionHandling.InsufficientBalanceException;
import bank_services_app.exceptionHandling.NegativeAmountException;
import bank_services_app.models.AccountDetails;
import bank_services_app.models.Customer;
import bank_services_app.models.Transaction;
import bank_services_app.Dto.request.TransactionRequest;
import bank_services_app.repositry.AccountRepo;
import bank_services_app.repositry.TransactionRepo;
import bank_services_app.services.MpinService;
import bank_services_app.util.AccountStatus;
import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class Core {

        private final AccountRepo accountRepo;
        private final TransactionRepo transactionRepo;
        private final MpinService mpinService;

        public Core(AccountRepo accountRepo, TransactionRepo transactionRepo, MpinService mpinService) {
            this.accountRepo = accountRepo;
            this.transactionRepo = transactionRepo;
            this.mpinService = mpinService;
        }


    public  String generateRef() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0,16).toUpperCase();
    }
    public void verifyAccount(AccountDetails accountDetails){
        if(accountDetails==null){
            throw new AccountNotFoundException("Account not found");
        }
    }
    public void verifyAmount(BigDecimal amount) {
        if( amount==null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new NegativeAmountException("Invalid Amount");
        }
    }
    public void verifyAccountStatus(AccountStatus accountStatus){
        if(accountStatus == AccountStatus.DEACTIVATE){
            throw new AccountDeactivatedException("Account is deactivated");
        }
    }
    public void verifyWithdraw(AccountDetails accountDetails, BigDecimal amount) {
        if(  accountDetails.getBalance().compareTo(amount)< 0 ){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
    }

    public String maskAccount(String acc){
        return "XXXXXX" + acc.substring(acc.length()-4);
    }
        @Transactional
        public TransactionalResponse executeSingle(String accountNumber, TransactionRequest request, Customer customer, TransactionType type, TransactionChannels channel) {
            AccountDetails acc = accountRepo.findByCustomerAndAccountNumber(customer, accountNumber);

            verifyAccount(acc);
            verifyAccountStatus(acc.getAccountStatus());
            verifyAmount(request.amount());
            mpinService.verifyMpinFormat(request.mpin());
            mpinService.verifyMpin(acc, request.mpin());

            if (type == TransactionType.DEBIT) {
                verifyWithdraw(acc, request.amount());
                acc.setBalance(acc.getBalance().subtract(request.amount()));

                Transaction tx = new Transaction();
                tx.setAccount(acc);
                tx.setDebit(request.amount());
                tx.setCredit(BigDecimal.ZERO);
                tx.setBalance(acc.getBalance());
                tx.setTransactionType(type);
                tx.setTransactionChannels(channel);
                tx.setTransactionTime(LocalDateTime.now());
                tx.setDetails("Debit via " + channel);
                tx.setReferenceNumber(generateRef());

                transactionRepo.save(tx);

            } else {
                acc.setBalance(acc.getBalance().add(request.amount()));

                Transaction tx = new Transaction();
                tx.setAccount(acc);
                tx.setCredit(request.amount());
                tx.setDebit(BigDecimal.ZERO);
                tx.setBalance(acc.getBalance());
                tx.setTransactionType(type);
                tx.setTransactionChannels(channel);
                tx.setTransactionTime(LocalDateTime.now());
                tx.setDetails("Credit via " + channel);
                tx.setReferenceNumber(generateRef());


                transactionRepo.save(tx);
            }
            return new TransactionalResponse(
                    acc.getAccountNumber(),
                    type,
                    channel,
                    request.amount(),
                    acc.getBalance(),
                    LocalDateTime.now()
            );
        }

        @Transactional
        public TransactionalResponse executeTransfer(AccountDetails sender , AccountDetails receiver,  BigDecimal amount, String mpin, TransactionChannels channel, String senderIdentity , String receiverIdentity) {

            verifyAccount(sender);
            verifyAccount(receiver);

            verifyAccountStatus(sender.getAccountStatus());
            verifyAccountStatus(receiver.getAccountStatus());

            verifyAmount(amount);

            mpinService.verifyMpinFormat(mpin);
            mpinService.verifyMpin(sender, mpin);
            verifyWithdraw(sender, amount);

            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            // sender

            Transaction txSender = new Transaction();
            txSender.setAccount(sender);
            txSender.setCredit(BigDecimal.ZERO);
            txSender.setDebit(amount);
            txSender.setBalance(sender.getBalance());
            txSender.setTransactionType(TransactionType.DEBIT);
            txSender.setTransactionChannels(channel);
            txSender.setTransactionTime(LocalDateTime.now());
            txSender.setDetails("Transfer to " +maskAccount(receiverIdentity) +" via " + channel);
            txSender.setReferenceNumber(generateRef());



            //receiver

            Transaction txReceiver = new Transaction();
            txReceiver.setAccount(receiver);
            txReceiver.setCredit(amount);
            txReceiver.setDebit(BigDecimal.ZERO);
            txReceiver.setBalance(receiver.getBalance());
            txReceiver.setTransactionType(TransactionType.CREDIT);
            txReceiver.setTransactionChannels(channel);
            txReceiver.setTransactionTime(LocalDateTime.now());
            txReceiver.setDetails("Transfer from " +maskAccount(senderIdentity) +" via " + channel);
            txReceiver.setReferenceNumber(generateRef());


            transactionRepo.save(txReceiver);
            transactionRepo.save(txSender);

            return new TransactionalResponse(
                    sender.getAccountNumber(),
                    TransactionType.DEBIT,
                    channel,
                    amount,
                    sender.getBalance(),
                    LocalDateTime.now()
            );
        }
    }

