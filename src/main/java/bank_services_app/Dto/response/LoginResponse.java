package bank_services_app.Dto.response;

import bank_services_app.util.Role;

public record LoginResponse(String token, String username, Role role) {
}
