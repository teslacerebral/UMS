package com.example.UMS.Controller;


import com.example.UMS.DTO.UserDto;
import com.example.UMS.Exception.RegistrationException;
import com.example.UMS.Model.User;
import com.example.UMS.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


            @Autowired
            UserService userService;
            @PostMapping("/register")
            public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
                try {
                    Set<String> roles = new HashSet<>();
                    roles.add("ROLE_USER"); // Default role for registration
                    userService.registerNewUser(userDto,roles);
                    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
                } catch (RegistrationException ex) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + ex.getMessage());
                } catch (Exception ex) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + ex.getMessage());
                }
        }

}
