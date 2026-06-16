package bank_services_app.Dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StatementResponse( String referenceNumber ,BigDecimal credit, BigDecimal debit, BigDecimal balance, String details, String type, String channel, LocalDateTime time) {
}
