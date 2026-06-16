package bank_services_app.services;

import bank_services_app.exceptionHandling.InvalidMpin;
import bank_services_app.models.AccountDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MpinService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void verifyMpin(AccountDetails account, String mpin) {
        if (mpin == null || !encoder.matches(mpin, account.getMpin())) {
            throw new InvalidMpin("Invalid MPIN");
        }
    }

    public void verifyMpinFormat(String mpin) {
        if (mpin == null || !mpin.matches("\\d{6}")) {
            throw new InvalidMpin("MPIN must be 6 digits");
        }
    }
}
