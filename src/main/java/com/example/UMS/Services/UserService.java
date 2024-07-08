        package com.example.UMS.Services;

        import com.example.UMS.DTO.UserDto;
        import com.example.UMS.Enums.UserRole;
        import com.example.UMS.Exception.RegistrationException;
        import com.example.UMS.Model.Role;
        import com.example.UMS.Model.User;
        import com.example.UMS.Repository.RoleRepository;
        import com.example.UMS.Repository.UserRepository;
        import jakarta.transaction.Transactional;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;

        import java.util.HashSet;
        import java.util.Set;

        @Service
        public class UserService {

                @Autowired
                private UserRepository userRepository;

                @Autowired
                private RoleRepository roleRepository;

                @Autowired
                private PasswordEncoder passwordEncoder;

                @Transactional
                public Boolean registerNewUser(UserDto userDto, Set<String> roles) {
                        User newUser = new User();
                        newUser.setUsername(userDto.getUsername());
                        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                        newUser.setEmail(userDto.getEmail());
                        newUser.setFirstName(userDto.getFirstName());
                        newUser.setLastName(userDto.getLastName());
                        newUser.setPhoneNumber(userDto.getPhoneNumber());

                        Set<Role> userRoles = new HashSet<>();
                        for (String roleName : roles) {
                                UserRole userRole;
                                try {
                                        userRole = UserRole.valueOf(roleName);
                                } catch (IllegalArgumentException e) {
                                        throw new RegistrationException("Invalid role: " + roleName);
                                }

                                Role role = roleRepository.findByRoleName(userRole)
                                        .orElseThrow(() -> new RegistrationException("Role not found: " + roleName));
                                userRoles.add(role);
                        }
                        newUser.setRoles(userRoles);

                        if(userRepository.findByUsername(newUser.getUsername())!= null) {
                                        throw new RegistrationException("Username already exists: " + newUser.getUsername());

                        }
                        try {
                                userRepository.save(newUser);
                                return true;
                        } catch (Exception ex) {
                                // Log the exception
                                ex.printStackTrace();
                                return false;
                        }
                }
        }
