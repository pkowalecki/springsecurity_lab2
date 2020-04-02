package pl.kowalecki.springsecurity_lab2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kowalecki.springsecurity_lab2.entity.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByValue(String value);

}
