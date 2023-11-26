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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.netflix.eventbus.spi.FilterLanguage.Constant;

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

                        .requestMatchers(HttpMethod.POST, "/api/v2/create/slots").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/cancel/slots/{id}").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/slots").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/resource/{id}").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/appointment/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.POST, "/api/v2/cancel/appointment/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/user/getDoctor/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v2/user/getPatient/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v2/user/getAllDoctors").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v2/getAllAppointmentByPatientId").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getAllAppointmentByDoctorId").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getAllAppointments").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v2/create/medicine").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v2/update/medicine/{id}").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/delete/medicine/{id}").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getAllMedicine").hasAnyRole(Constants.ROLE_ADMIN,Constants.ROLE_DOCTOR,Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getMedicineById/{id}").hasAnyRole(Constants.ROLE_ADMIN,Constants.ROLE_PATIENT,Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getAllSlotsByDoctorId/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getMyAllSlots").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getNotificationForPatient").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/v2/getNotificationForDoctor").hasRole(Constants.ROLE_DOCTOR)
                        .requestMatchers(HttpMethod.PUT, "/api/v2/changePatientStatus/{notificationId}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/v2/changeDoctorStatus/{notificationId}").hasRole(Constants.ROLE_DOCTOR)

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