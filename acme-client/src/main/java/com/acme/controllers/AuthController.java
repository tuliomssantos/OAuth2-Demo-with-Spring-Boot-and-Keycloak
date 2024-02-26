package com.acme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private WebClient webClient;

    public AuthController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getUser(
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient client) {
        try {
            ResponseEntity<Object> response =
                    webClient.get().uri("http://localhost:8080/api/users/self")
                            .attributes(oauth2AuthorizedClient(client)).retrieve()
                            .toEntity(Object.class).block();

            return response;
        } catch (Exception ex) {
            if (ex instanceof WebClientResponseException) {
                WebClientResponseException webClientEx = (WebClientResponseException) ex;
                HttpStatusCode statusCode = webClientEx.getStatusCode();
                String responseBody = webClientEx.getResponseBodyAsString();
                return ResponseEntity.status(statusCode).body(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Internal server error occurred"));
            }
        }
    }

}
