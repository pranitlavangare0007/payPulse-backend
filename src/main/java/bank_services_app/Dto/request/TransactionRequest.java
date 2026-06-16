package bank_services_app.Dto.request;

import bank_services_app.util.TransactionChannels;

import java.math.BigDecimal;

public record TransactionRequest(String mpin , BigDecimal amount , TransactionChannels channel,String upiId , String accountNumberReceiver) {
}
