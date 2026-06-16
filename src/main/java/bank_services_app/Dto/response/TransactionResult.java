package bank_services_app.Dto.response;

import java.util.List;

public record TransactionResult(String accountNumber, List<StatementResponse> transactions) {
}
