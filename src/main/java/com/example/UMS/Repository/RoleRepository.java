package com.example.UMS.Repository;

import com.example.UMS.Enums.UserRole;
import com.example.UMS.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole roleName);


        boolean existsByRoleName(UserRole roleName);

}
