package bank_services_app.models;

import bank_services_app.util.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CustomerId;

    @Column(unique = true ,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true ,nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false ,unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}
