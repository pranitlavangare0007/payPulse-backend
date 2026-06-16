package bank_services_app.Dto.response;

import java.math.BigDecimal;

public record BalanceResponse(String accountNumber,BigDecimal balance) {
}
