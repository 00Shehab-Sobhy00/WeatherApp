package com.example.backend.service.impl;

import com.example.backend.controller.WeatherAPIController;
import com.example.backend.dto.request.LoginRequestDTO;
import com.example.backend.dto.request.RegisterUserDTO;
import com.example.backend.dto.response.AuthenticationResponseDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.handleUnauthorizedException;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    // returning a new token after signing up
    public AuthenticationResponseDTO register(RegisterUserDTO registerUserDTO) {
        logger.info("register() ");
        logger.debug("RegisterUserDTO: {}",registerUserDTO );
        var user = new User();
        if (userRepo.findByEmail(registerUserDTO.getEmail()).isPresent()){
            logger.error("Email already exists: {}", registerUserDTO.getEmail());
            throw new UserAlreadyExistException("Email already exist");
        }
        else {
            user.setName(registerUserDTO.getName());
            user.setEmail(registerUserDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            user.setPhone(registerUserDTO.getPhone());
            user.setRoles(ERole.USER);
            userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponseDTO.builder()
                    .userId(user.getId())
//                   .role(user.getRoles().name())
                    .token(jwtToken)
                    .build();
        }


    }
//AuthenticationResponse tokenGenration = new AuthenticationResponse();
//        tokenGenration.setToken(jwtToken);
//        return  tokenGenration;


    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        logger.info("login()");
    logger.debug("LoginRequestDTO : {} " ,request);
        // this authentication manager will do all the job for me encase the user name of password are not corrent
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword()));

        Optional<User> user = userRepo.findByEmail(request.getEmail());

        if (user.isPresent()) {
                var jwtToken = jwtService.generateToken(user.get());
                return AuthenticationResponseDTO.builder()
                .userId(user.get().getId())
//              .role(user.get().getRoles().name())
                .token(jwtToken)
                .build();
                } else{
                logger.error("invalid email or password :{} ", request);
                throw new UsernameNotFoundException(" invalid email or password ");
                }

    }
}


//
