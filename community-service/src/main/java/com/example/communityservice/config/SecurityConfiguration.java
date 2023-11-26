package com.example.communityservice.config;

import com.example.communityservice.security.JwtAuthenticationFilter;
import com.example.communityservice.utill.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
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
                        .requestMatchers(HttpMethod.POST, "/api/community/create/group").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/community/update/group/{groupId}").hasRole(Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/get/AllGroups").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/community/delete/group/{groupId}").hasRole(Constants.ROLE_ADMIN)

                        .requestMatchers(HttpMethod.POST, "/api/community/create/post/{id}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/community/update/post/{postId}").hasRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.DELETE, "/api/community/delete/post/{id}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/getByPatientIdAndGroupId/{groupId}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/getByGroupId/{groupId}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/community/upVotes/{id}").hasAnyRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/community/downVotes/{id}").hasAnyRole(Constants.ROLE_PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/community/getAllUpVotes/{id}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/getAllDownVotes/{id}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/getSinglePost/{id}").hasAnyRole(Constants.ROLE_PATIENT,Constants.ROLE_ADMIN)


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

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
