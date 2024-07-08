package com.example.UMS.Initializer;

import com.example.UMS.Enums.UserRole;
import com.example.UMS.Model.Role;
import com.example.UMS.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(UserRole.values()).forEach(userRole -> {
            if (!roleRepository.existsByRoleName(userRole)) {
                Role role = new Role();
                role.setRoleName(userRole);
                roleRepository.save(role);
            }
        });
    }
}