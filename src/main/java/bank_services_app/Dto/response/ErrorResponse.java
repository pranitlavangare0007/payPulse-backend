package bank_services_app.Dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime time) {
}
