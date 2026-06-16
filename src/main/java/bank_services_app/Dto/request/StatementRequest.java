package bank_services_app.Dto.request;

import java.time.LocalDate;

public record StatementRequest( LocalDate fromDate , LocalDate toDate) {
}
