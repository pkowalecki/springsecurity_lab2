package pl.kowalecki.springsecurity_lab2.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kowalecki.springsecurity_lab2.entity.AppUser;
import pl.kowalecki.springsecurity_lab2.entity.VerificationToken;
import pl.kowalecki.springsecurity_lab2.repo.AppUserRepo;
import pl.kowalecki.springsecurity_lab2.repo.VerificationTokenRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailSenderService mailSenderService;

    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerificationTokenRepo verificationTokenRepo, MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
    }


    public void addNewUser(AppUser user, HttpServletRequest request) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);
        
        String url = request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" + token;

            mailSenderService.sendMail(user.getUsername(), "VerificationToken", url, false);


    }

    public void addNewAdmin(AppUser user, HttpServletRequest request) throws MessagingException {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);

        String url = request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" + token;

            mailSenderService.sendMail("kowalecki13pl@o2.pl", "Admin request:" + user.getUsername(), url,false);


    }


    public void verifyToken(String token){
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
    }

    public void verifyAdminToken(String token){
    AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
    appUser.setEnabled(true);
    appUser.setRole("ROLE_ADMIN");
    appUserRepo.save(appUser);
    }
}
