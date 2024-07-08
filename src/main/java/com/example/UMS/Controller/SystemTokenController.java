package com.example.UMS.Controller;

import com.example.UMS.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemTokenController {

    @Autowired
    private AuthenticationService authenticationService;

    // Endpoint to generate system token using Basic Authentication
    @GetMapping("/api/system/token")
    public ResponseEntity<String> generateSystemToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String systemToken = authenticationService.authenticateAndGenerateSystemToken(authorizationHeader);
            return ResponseEntity.ok(systemToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating system token: " + e.getMessage());
        }
    }
}
