package com.example.backend.service;

import com.example.backend.dto.request.LoginRequestDTO;
import com.example.backend.dto.request.RegisterUserDTO;
import com.example.backend.dto.response.AuthenticationResponseDTO;

public interface AuthService {

      AuthenticationResponseDTO register(RegisterUserDTO registerUserDTO);
    public AuthenticationResponseDTO login(LoginRequestDTO request);
}
