package com.acme.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JWTConverter jwtAuthConverter;

    public SecurityConfiguration(JWTConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> {
            auth.anyRequest().authenticated();

        }).sessionManagement(sessionManagement -> {
            sessionManagement.sessionCreationPolicy(STATELESS);
        }).oauth2ResourceServer(oauth2ResourceServer -> {
            oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
        });

        return http.build();
    }
}
