package bank_services_app.controller;

import bank_services_app.Dto.response.LoginResponse;
import bank_services_app.Dto.response.ProfileResponse;
import bank_services_app.models.Customer;
import bank_services_app.Dto.request.LoginRequest;
import bank_services_app.models.EmailVerification;
import bank_services_app.repositry.EmailVerificationRepo;
import bank_services_app.services.CustomerServices;
import bank_services_app.services.JwtService;
import bank_services_app.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailVerificationRepo verificationRepo;



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){

        System.out.println("controller reached");
        EmailVerification ev = verificationRepo.findByEmail(customer.getEmail())
                .orElseThrow(() -> new RuntimeException("Verify email first"));

        if(!ev.isVerified())
            throw new RuntimeException("Email not verified");
        if(customerServices.getUser(customer.getUsername()) != null){
            return new ResponseEntity<>("Customer already exists", HttpStatus.CONFLICT);
        }
        customer.setRole(Role.CUSTOMER);
        customerServices.saveUser(customer);
        verificationRepo.delete(ev);
        System.out.println("success");
        return new ResponseEntity<>("Customer registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest  loginRequest){

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password()
                )
        );
        Customer dbUser = customerServices.getUser(loginRequest.username());
        String token = jwtService.generateToken(
                dbUser.getUsername(),
                dbUser.getRole().name()
        );


        LoginResponse loginResponse = new LoginResponse(token,dbUser.getUsername(),dbUser.getRole());

        return new ResponseEntity<>(loginResponse,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getCustomerProfile(Authentication authentication){

        String username = authentication.getName();
        System.out.println("username = " + username);
       return new ResponseEntity<>(customerServices.getUserProfile(username),HttpStatus.OK);

    }
}
