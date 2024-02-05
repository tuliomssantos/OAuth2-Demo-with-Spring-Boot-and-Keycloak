package com.acme.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;
import java.util.Collections;
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

    // @GetMapping("/logout")
    // public ResponseEntity<Object> logout() {
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(Collections.singletonMap("message", "Logout successful"));
    // }

    // @GetMapping("/logout")
    // public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse
    // response) {
    // String refreshToken = (String) request.getSession().getAttribute("refreshToken");

    // // Send a POST request to the Keycloak logout endpoint
    // RestTemplate restTemplate = new RestTemplate();
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    // MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    // map.add("refresh_token", refreshToken);
    // HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
    // restTemplate.postForLocation(
    // "http://localhost:2727/realms/acme-corporation/protocol/openid-connect/logout",
    // requestEntity);

    // // Clear the current session
    // request.getSession().invalidate();

    // return ResponseEntity.status(HttpStatus.OK)
    // .body(Collections.singletonMap("message", "Logout successful"));
    // }
}
