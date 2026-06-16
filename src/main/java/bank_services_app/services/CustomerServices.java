package bank_services_app.services;

import bank_services_app.Dto.response.ProfileResponse;
import bank_services_app.models.Customer;
import bank_services_app.repositry.CustomerRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServices {
    @Autowired
    CustomerRepo customerRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public Customer getUser(String username) {

        return customerRepo.findByUsername(username);
    }

    public void saveUser(@NonNull Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
         customerRepo.save(customer);
    }

    public ProfileResponse getUserProfile(String username) {
        Customer customer = customerRepo.findByUsername(username);
         return new ProfileResponse(customer.getUsername(),
                 customer.getName(),
                 customer.getEmail(),
                 customer.getAddress(),
                 customer.getPhoneNumber()
                 );
    }
}
