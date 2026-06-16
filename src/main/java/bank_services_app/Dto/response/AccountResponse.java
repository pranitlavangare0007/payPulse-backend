package bank_services_app.Dto.response;

import bank_services_app.util.AccountPurpose;
import bank_services_app.util.AccountStatus;
import bank_services_app.util.AccountType;

import java.math.BigDecimal;

public record AccountResponse(
        String accountNumber,
        AccountType accountType,
        AccountStatus accountStatus,
        AccountPurpose accountPurpose,
        BigDecimal balance,
        String upiId
) {
}
