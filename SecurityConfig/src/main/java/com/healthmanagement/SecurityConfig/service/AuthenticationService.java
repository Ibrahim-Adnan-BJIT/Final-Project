package com.healthmanagement.SecurityConfig.service;

import com.healthmanagement.SecurityConfig.dto.LogInDto;
import com.healthmanagement.SecurityConfig.dto.LoginResponseDto;
import com.healthmanagement.SecurityConfig.dto.RegisterDto;
import com.healthmanagement.SecurityConfig.dto.RegistrationResponseDto;

public interface AuthenticationService {
      RegistrationResponseDto createPatient(RegisterDto registerDto);
    RegistrationResponseDto createDoctor(RegisterDto registerDto);
    RegistrationResponseDto createAdmin(RegisterDto registerDto);

    LoginResponseDto authenticateUser(LogInDto logInDto);
}
