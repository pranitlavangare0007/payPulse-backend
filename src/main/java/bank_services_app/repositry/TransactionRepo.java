package bank_services_app.repositry;

import bank_services_app.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccount_AccountNumberAndTransactionTimeBetweenOrderByTransactionTimeDesc(
            String accountNumber,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Transaction> findTop20ByAccount_AccountNumberOrderByTransactionTimeDesc(String accountNumber);
}
