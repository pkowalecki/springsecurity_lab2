package pl.kowalecki.springsecurity_lab2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    @GetMapping("/forAll")
    public String forAll(){
        return "Żegnaj";
    }
    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return "Witaj użytkowniku "+ principal.getName();
    }
    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "Witaj adminie "+ principal.getName();
    }
}
