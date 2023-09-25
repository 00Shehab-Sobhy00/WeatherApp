package com.example.backend.config;


import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepo userRepo;
    @Bean
    //UserDetailsService interface only contains  : FUNCTIONAL INTERFACE
    //UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public UserDetailsService userDetailsService(){
//        loadUserByUsername
         return username -> {
             Optional<User> optionalUser = userRepo.findByEmail(username);
              if (optionalUser.isPresent())
                  return optionalUser.get();
              else throw new UsernameNotFoundException("User not found ");
         };
    }



    @Bean
    public AuthenticationProvider authenticationProvider(){
          // data access object which is responsible to fetch the user details and also encode password and so on so forth

        var authProvider = new DaoAuthenticationProvider();
        // specifying some property's

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
         
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager and auth manager : is the one whose responsible to manager authentication
    // has a bunch of methods and the one is gonna help us to authenticate user based on just username and password

    @Bean
    public AuthenticationManager authManager (AuthenticationConfiguration configuration) throws Exception {
        // auth config hold already information about auth manager
        return configuration.getAuthenticationManager();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

