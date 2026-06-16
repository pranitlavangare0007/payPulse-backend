package bank_services_app.models;


import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionID;

    @Column(unique = true, nullable = false)
    private String referenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number" , nullable = false)
    private AccountDetails account;

    @Column(name = "credit")
    private BigDecimal credit= BigDecimal.ZERO;

    @Column(name = "debit")
    private BigDecimal debit= BigDecimal.ZERO;

    @Column(name = "date_and_time")
    private LocalDateTime transactionTime;

    @Column(name = "details")
    private String details=" - ";

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "fund_transfer_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionChannels transactionChannels;

}
