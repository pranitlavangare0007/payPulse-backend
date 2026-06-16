package bank_services_app.Dto.request;

import bank_services_app.util.AccountPurpose;
import bank_services_app.util.AccountType;

public record AccountRecord(String mpin, AccountType accountType , AccountPurpose accountPurpose) {
}
