package com.healthmanagement.SecurityConfig.service.impl;

import com.healthmanagement.SecurityConfig.dto.LogInDto;
import com.healthmanagement.SecurityConfig.dto.LoginResponseDto;
import com.healthmanagement.SecurityConfig.dto.RegisterDto;
import com.healthmanagement.SecurityConfig.dto.RegistrationResponseDto;
import com.healthmanagement.SecurityConfig.entity.*;
import com.healthmanagement.SecurityConfig.exception.AuthenticationExceptions;
import com.healthmanagement.SecurityConfig.exception.EmailAlreadyExists;
import com.healthmanagement.SecurityConfig.repository.AdminRepo;
import com.healthmanagement.SecurityConfig.repository.DoctorRepo;
import com.healthmanagement.SecurityConfig.repository.PatientRepo;
import com.healthmanagement.SecurityConfig.repository.UserRepository;
import com.healthmanagement.SecurityConfig.security.JwtService;
import com.healthmanagement.SecurityConfig.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private DoctorRepo doctorRepository;
    private PatientRepo patientRepository;
    private AdminRepo adminRepository;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    @Override
    public RegistrationResponseDto createPatient(RegisterDto registerDto) {
         if(userRepository.findByEmail(registerDto.getEmail()).isPresent())
         {
             throw new EmailAlreadyExists("This Email is already Taken by someone");
         }

         Role roles= Role.valueOf("PATIENT");
         var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(roles)
                .gender(Gender.valueOf(registerDto.getGender()))
                .build();

        User newUser = userRepository.save(user);
       Patient patient=new Patient();
       patient.setUserId(newUser.getUserId());
       patientRepository.save(patient);
        var jwtToken = jwtService.generateToken(user);

       RegistrationResponseDto registrationResponseDto=new RegistrationResponseDto();
       registrationResponseDto.setUserId(newUser.getUserId());
       registrationResponseDto.setEmail(newUser.getEmail());
       registrationResponseDto.setRole(String.valueOf(roles));
       registrationResponseDto.setToken(jwtToken);
       return  registrationResponseDto;
    }

    @Override
    public RegistrationResponseDto createDoctor(RegisterDto registerDto) {

        if(userRepository.findByEmail(registerDto.getEmail()).isPresent())
        {
            throw new EmailAlreadyExists("This Email is already Taken by someone");
        }

        Role roles= Role.valueOf("DOCTOR");
        var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(roles)
                .gender(Gender.valueOf(registerDto.getGender()))
                .build();

        User newUser = userRepository.save(user);
         Doctor doctor=new Doctor();
         doctor.setUserId(newUser.getUserId());
         doctorRepository.save(doctor);
        var jwtToken = jwtService.generateToken(user);

        RegistrationResponseDto registrationResponseDto=new RegistrationResponseDto();
        registrationResponseDto.setUserId(newUser.getUserId());
        registrationResponseDto.setEmail(newUser.getEmail());
        registrationResponseDto.setRole(String.valueOf(roles));
        registrationResponseDto.setToken(jwtToken);
        return  registrationResponseDto;
    }

    @Override
    public RegistrationResponseDto createAdmin(RegisterDto registerDto) {
        if(userRepository.findByEmail(registerDto.getEmail()).isPresent())
        {
            throw new EmailAlreadyExists("This Email is already Taken by someone");
        }

        Role roles= Role.valueOf("ADMIN");
        var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(roles)
                .gender(Gender.valueOf(registerDto.getGender()))
                .build();

        User newUser = userRepository.save(user);
         Admin admin=new Admin();
         admin.setUserId(newUser.getUserId());
         adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(user);

        RegistrationResponseDto registrationResponseDto=new RegistrationResponseDto();
        registrationResponseDto.setUserId(newUser.getUserId());
        registrationResponseDto.setEmail(newUser.getEmail());
        registrationResponseDto.setRole(String.valueOf(roles));
        registrationResponseDto.setToken(jwtToken);
        return  registrationResponseDto;

    }

    @Override
    public LoginResponseDto authenticateUser(LogInDto logInDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDto.getEmail(),
                            logInDto.getPassword()
                    )
            );
            // Check is user exists or not
            var user = userRepository.findByEmail(logInDto.getEmail())
                    .orElseThrow(() -> new AuthenticationExceptions("User not Found"));

            var jwtToken = jwtService.generateToken(user);
            return LoginResponseDto.builder()
                    .email(user.getEmail())
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            // Handle invalid credentials (wrong email or password)
            throw new AuthenticationExceptions( "Invalid email or password");
        } catch (ExpiredJwtException e) {
            // Handle expired JWT tokens
            throw new AuthenticationExceptions( "Session has expired. Please log in again");
        }  catch (Exception e) {
            // Handle other exceptions here
            throw new AuthenticationExceptions( "Authentication failed");
        }
    }
    }

