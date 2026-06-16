package bank_services_app.services;

import bank_services_app.models.AccountDetails;
import bank_services_app.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp){

        try {
            System.out.println("send otp function");

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject("PayPulse Email Verification");
            message.setText(
                    "Welcome to PayPulse Bank\n\n" +
                            "Your verification OTP is: " + otp +
                            "\nValid for 5 minutes."
            );

            mailSender.send(message);

            System.out.println("otp sent");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendAccountDetailsEmail(String to, AccountDetails details, Role role){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("PayPulse Account Details");
        message.setText(
                "Welcome to PayPulse Bank\n\n" +
                        "Your Account Number is : " + details.getAccountNumber() + "\n" +
                        " Account type is : " + details.getAccountType() + "\n"+
                        " Account purpose is : " + details.getAccountPurpose() + "\n"+
                        " role : " + role + "\n"+
                        " Upi Id is : " + details.getUpiId()
        );

        mailSender.send(message);
    }


}
