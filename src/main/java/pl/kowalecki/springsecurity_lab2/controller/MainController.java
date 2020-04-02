package pl.kowalecki.springsecurity_lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kowalecki.springsecurity_lab2.entity.AppUser;
import pl.kowalecki.springsecurity_lab2.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService){
        this.userService=userService;
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signUp")
    public ModelAndView signUp(){

        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser user, HttpServletRequest request,
                                 @RequestParam(defaultValue = "false")boolean adminCheckBox) throws MessagingException {

        userService.addNewUser(user, request);
        if(adminCheckBox) userService.addNewAdmin(user,request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken (@RequestParam String token){
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");

    }

    @RequestMapping("/verify-admin")
    public ModelAndView verifyAdminToken (@RequestParam String token){
        userService.verifyAdminToken(token);
        return new ModelAndView("redirect:/login");

    }
}
