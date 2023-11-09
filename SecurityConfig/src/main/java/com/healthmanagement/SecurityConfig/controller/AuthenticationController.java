package com.healthmanagement.SecurityConfig.controller;


import com.healthmanagement.SecurityConfig.dto.LogInDto;
import com.healthmanagement.SecurityConfig.dto.LoginResponseDto;
import com.healthmanagement.SecurityConfig.dto.RegisterDto;
import com.healthmanagement.SecurityConfig.dto.RegistrationResponseDto;
import com.healthmanagement.SecurityConfig.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/register/patient")
    public RegistrationResponseDto registerPatient(@RequestBody RegisterDto request){
        return authenticationService.createPatient(request);
    }
    @PostMapping("/register/doctor")
    public RegistrationResponseDto registerDoctor(@RequestBody RegisterDto request){
        return authenticationService.createDoctor(request);
    }
    @PostMapping("/register/admin")
    public RegistrationResponseDto registerAdmin(@RequestBody RegisterDto request){
        return authenticationService.createAdmin(request);
    }

    @PostMapping("/login")
    public LoginResponseDto authenticate(@RequestBody LogInDto request){
        return authenticationService.authenticateUser(request);
    }
}