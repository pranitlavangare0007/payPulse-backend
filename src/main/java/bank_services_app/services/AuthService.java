package bank_services_app.services;

import bank_services_app.exceptionHandling.InvalidOtpException;
import bank_services_app.models.EmailVerification;
import bank_services_app.repositry.EmailVerificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private EmailVerificationRepo repo;

    @Autowired
    private EmailService emailService;

    private String generateOtp(){
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }


    public String sendOtp(String email){

        String otp = generateOtp();

        EmailVerification ev = repo.findByEmail(email)
                .orElse(new EmailVerification());

        ev.setEmail(email);
        ev.setOtp(otp);
        ev.setVerified(false);
        ev.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        repo.save(ev);

        System.out.println("otp saved");
        emailService.sendOtpEmail(email, otp);
        System.out.println("otp sent");
        return "OTP Sent";
    }


    public String verifyOtp(String email, String otp){

        EmailVerification ev = repo.findByEmail(email)
                .orElseThrow(() -> new InvalidOtpException("Request OTP first"));

        if(ev.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new InvalidOtpException("OTP Expired");

        if(!ev.getOtp().equals(otp))
            throw new InvalidOtpException("Invalid OTP");

        ev.setVerified(true);
        repo.save(ev);

        return "Email Verified";
    }
}
