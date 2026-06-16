package bank_services_app.Dto.response;

import bank_services_app.util.TransactionChannels;
import bank_services_app.util.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionalResponse(String accountNumber,
                                    TransactionType type,
                                    TransactionChannels channel,
                                    BigDecimal amount,
                                    BigDecimal balance,
                                    LocalDateTime time) {
}
