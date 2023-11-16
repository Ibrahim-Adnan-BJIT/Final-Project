package com.example.appointmentservice.config;

import com.example.appointmentservice.security.JwtAuthenticationFilter;
import com.example.appointmentservice.utill.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import static com.netflix.eventbus.spi.FilterLanguage.Constant;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/api/v2/create/slots").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/cancel/slots/{id}").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/slots").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/resource/{id}").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/appointment/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.POST, "/api/v2/cancel/appointment/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/user/getDoctor/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v2/user/getPatient/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/getAllDoctors").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v2/getAllAppointmentByPatientId/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.POST, "/api/v2/getAllAppointmentByDoctorId/{id}").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/getAllAppointments").hasRole(Constants.ROLE_ADMIN)

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}