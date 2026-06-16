package bank_services_app.Dto.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record StatementResult(String accountNumber, LocalDate fromDate, LocalDate toDate,
                              List<StatementResponse> transactions) implements Serializable {
}
