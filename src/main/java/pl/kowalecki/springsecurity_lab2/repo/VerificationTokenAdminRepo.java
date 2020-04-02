package pl.kowalecki.springsecurity_lab2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kowalecki.springsecurity_lab2.entity.VerificationTokenAdmin;

public interface VerificationTokenAdminRepo extends JpaRepository<VerificationTokenAdmin, Long> {

    VerificationTokenAdmin findByValue(String value);
}
