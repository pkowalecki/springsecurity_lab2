package pl.kowalecki.springsecurity_lab2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kowalecki.springsecurity_lab2.entity.AppUser;
import pl.kowalecki.springsecurity_lab2.repo.AppUserRepo;

import java.util.Collection;

@Component
public class Start {

     private PasswordEncoder passwordEncoder;
     private AppUserRepo appUserRepo;

     @Autowired
    public Start(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;

        if (appUserRepo.findAllByUsername("kowalecki13pl@o2.pl") == null){
        AppUser superAdmin = new AppUser();
            superAdmin.setUsername("kowalecki13pl@o2.pl");
            superAdmin.setPassword(passwordEncoder.encode("123456789"));
            superAdmin.setRole("ROLE_ADMIN");
            superAdmin.setEnabled(true);
        appUserRepo.save(superAdmin);
        }
    }
}
