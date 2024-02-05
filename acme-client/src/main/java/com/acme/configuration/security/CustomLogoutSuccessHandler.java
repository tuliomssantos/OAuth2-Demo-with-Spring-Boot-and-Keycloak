package com.acme.configuration.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Value("${frontend.url}")
    private String frontendUrl;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // it should not redirect
        response.setStatus(HttpServletResponse.SC_OK);
    }

}


// import java.io.IOException;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.*;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.core.oidc.OidcIdToken;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

// @Component
// public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {



// // @Value("${keycloak.realmUrl}")
// // private String realmUrl;



// @Override
// public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
// Authentication authentication) throws IOException, ServletException {
// sendBackchannelLogoutRequest(authentication);
// // super.onLogoutSuccess(request, response, authentication);
// response.setStatus(HttpServletResponse.SC_OK);
// }

// private void sendBackchannelLogoutRequest(Authentication authentication) {
// String accessToken = obtainAccessToken(authentication);

// HttpHeaders headers = new HttpHeaders();
// headers.setBearerAuth(accessToken);

// HttpEntity<String> entity = new HttpEntity<>(headers);

// String logoutEndpoint =
// "http://localhost:2727/realms/acme-corporation" + "/protocol/openid-connect/logout";
// RestTemplate restTemplate = new RestTemplate();

// ResponseEntity<String> responseEntity =
// restTemplate.exchange(logoutEndpoint, HttpMethod.POST, entity, String.class);

// if (responseEntity.getStatusCode() == HttpStatus.OK) {
// System.out.println("Backchannel logout request successful");
// } else {
// System.err.println("Failed to send backchannel logout request");
// }
// }

// private String obtainAccessToken(Authentication authentication) {
// if (authentication.getPrincipal() instanceof OidcUser) {
// OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
// OidcIdToken idToken = oidcUser.getIdToken();
// if (idToken != null) {
// return idToken.getTokenValue();
// }
// }
// throw new IllegalArgumentException("Unable to obtain access token");
// }
// }
