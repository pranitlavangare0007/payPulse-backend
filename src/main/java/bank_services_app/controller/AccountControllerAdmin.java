package bank_services_app.controller;

import bank_services_app.Dto.response.AccountResponse;
import bank_services_app.models.Customer;
import bank_services_app.services.AdminServices;
import bank_services_app.services.CustomerServices;
import bank_services_app.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AccountControllerAdmin {


    private final AdminServices adminServices;
    private final CustomerServices customerServices;

    public AccountControllerAdmin(AdminServices adminServices ,CustomerServices customerServices) {
        this.adminServices = adminServices;
        this.customerServices=customerServices;
    }

    @GetMapping("/getAll")
    public List<AccountResponse> getAllAccounts(Authentication authentication){

        String username = authentication.getName();
        Customer customer=customerServices.getUser(username);

        return adminServices.getAllAccounts(customer);
    }

}
