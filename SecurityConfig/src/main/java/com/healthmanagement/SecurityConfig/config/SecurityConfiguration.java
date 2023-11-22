package com.healthmanagement.SecurityConfig.config;


import com.healthmanagement.SecurityConfig.security.JwtAuthenticationFilter;
import com.healthmanagement.SecurityConfig.utill.Constants;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/v2/auth/register/patient").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v2/auth/register/doctor").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v2/auth/register/admin").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v2/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/profile").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/v2/user/update/profile").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/v2/user/skills").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/getAllDoctors").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/getPatientName/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/getDoctorName/**").permitAll()
                        .requestMatchers("/api/v2/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        //config.addAllowedHeader("Authorization");

        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Cache-Control"
        ));

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
