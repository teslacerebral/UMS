package com.example.UMS.Services;

import com.example.UMS.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // Autowire your UserDetailsService

    // Method to authenticate user and generate system token
    public String authenticateAndGenerateSystemToken(String authorizationHeader) throws AuthenticationException {
        // Extract username and password from Basic Auth header
        String[] credentials = extractCredentials(authorizationHeader);
        String username = credentials[0];
        String password = credentials[1];

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // If authentication successful, generate system token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateTokenWithClaims(userDetails.getUsername(), createSystemClaims(userDetails));
    }

    // Create system claims (customize as needed)
    private Map<String, Object> createSystemClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "system_user");
        // Add more custom claims as needed
        return claims;
    }

    // Extract username and password from Basic Auth header
    private String[] extractCredentials(String authorizationHeader) {
        // Example: "Basic base64EncodedString"
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes);
        return decodedString.split(":", 2); // Splits into username and password
    }
}


