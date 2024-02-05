package com.acme.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${frontend.url}")
    private String frontendUrl;

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final OAuth2AuthenticationEntrypoint oAuth2AuthenticationEntrypoint;



    public SecurityConfiguration(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
            CustomLogoutSuccessHandler customLogoutSuccessHandler,
            OAuth2AuthenticationEntrypoint oAuth2AuthenticationEntrypoint) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.oAuth2AuthenticationEntrypoint = oAuth2AuthenticationEntrypoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable()).authorizeHttpRequests(auth -> {

                    auth.anyRequest().authenticated();

                }).exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer
                            .authenticationEntryPoint(oAuth2AuthenticationEntrypoint);
                }).oauth2Login(oauth2Login -> {
                    oauth2Login.successHandler(oAuth2LoginSuccessHandler);
                }).logout(logout -> {



                    logout.logoutUrl("/api/auth/logout");

                    logout.deleteCookies("JSESSIONID");

                    logout.clearAuthentication(true);

                    logout.invalidateHttpSession(true);

                    logout.logoutSuccessHandler(customLogoutSuccessHandler);

                });

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(frontendUrl));

        configuration.addAllowedHeader("*");

        configuration.addAllowedMethod("*");

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return urlBasedCorsConfigurationSource;

    }
}
