package com.example.backend.controller;


import com.example.backend.dto.request.LoginRequestDTO;
import com.example.backend.dto.request.RegisterUserDTO;
import com.example.backend.dto.response.AuthenticationResponseDTO;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8080/weather/register")
    @RestController
    @RequestMapping("")
    @RequiredArgsConstructor
    @CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register (@RequestBody RegisterUserDTO registerUserDTO){

        logger.info(" register() called");
        logger.debug("RegisterUser : {}" , registerUserDTO);
       return ResponseEntity.ok(authService.register(registerUserDTO));
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login (@RequestBody LoginRequestDTO authRequest){
        logger.info(" login() called");
        logger.debug("Email Login : {}" , authRequest);
        return ResponseEntity.ok(authService.login(authRequest));
    }
}
