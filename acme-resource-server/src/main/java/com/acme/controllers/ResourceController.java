package com.acme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin("*")
public class ResourceController {

    @GetMapping
    @PreAuthorize("hasRole('ACME_CORPORATION_ADMIN')")
    public ResponseEntity<Object> getAdminResource() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Resource from Acme Corporation"));
    }

}
