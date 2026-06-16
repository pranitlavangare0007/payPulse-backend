package bank_services_app.models;


import bank_services_app.util.AccountPurpose;
import bank_services_app.util.AccountStatus;
import bank_services_app.util.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_details")
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;

    @Column(name = "account_number" , nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance=BigDecimal.ZERO;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus=AccountStatus.ACTIVE;

    @Column(name = "mpin" ,nullable = false)
    private String mpin;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "account_purpose")
    @Enumerated(EnumType.STRING)
    private AccountPurpose accountPurpose;

    @Column(nullable = false,unique = true)
    private String upiId;


}
