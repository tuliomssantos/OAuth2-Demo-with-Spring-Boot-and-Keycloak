package com.acme.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/self")

    public ResponseEntity<Object> getUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("email", email);

        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }
}
