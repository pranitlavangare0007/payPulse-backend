package bank_services_app.repositry;

import bank_services_app.models.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepo extends JpaRepository<EmailVerification,Long> {
    Optional<EmailVerification> findByEmail(String email);
}
