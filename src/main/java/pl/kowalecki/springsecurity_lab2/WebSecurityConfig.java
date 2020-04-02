package pl.kowalecki.springsecurity_lab2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        User userAdmin = new User("PiotrekAdmin", getPasswordEncoder().encode("PiotrekAdmin"), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
//        User userUser = new User("PiotrekUser", getPasswordEncoder().encode("PiotrekUser"), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//
//
//        auth.inMemoryAuthentication().withUser(userAdmin);
//        auth.inMemoryAuthentication().withUser(userUser);

        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole("ADMIN")
                .antMatchers("/forUser").hasRole("USER")
                .antMatchers("/signUp").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/forUser").permitAll()
                .and()
                .logout().logoutSuccessUrl("/forAll");
    }
}

 