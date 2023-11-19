package com.example.ClinicalDecisionSupportSystemService.config;

import com.example.ClinicalDecisionSupportSystemService.security.JwtAuthenticationFilter;
import com.example.ClinicalDecisionSupportSystemService.utill.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

                        .requestMatchers(HttpMethod.PUT, "/api/healthdata/update/record").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.POST, "/api/progress/create").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/progress/update/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/progress/getCompletedProgress").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/progress/getInCompletedProgress").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.DELETE, "/api/progress/delete/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/recommendation/update/blood").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/recommendation/update/bmi").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/recommendation/update/diabetes").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/recommendation/update/allergy").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v2/user/getPatient/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/recommendation/getRecommendationByPatientId").hasRole(Constants.ROLE_PATIENT)



                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}