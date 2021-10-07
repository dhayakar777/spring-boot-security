package com.tutorials.tutorialservice.repository;

import com.tutorials.tutorialservice.models.Role;
import com.tutorials.tutorialservice.models.UserRole;
import com.tutorials.tutorialservice.models.web.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository  extends JpaRepository<UserRole, String> {
    List<Optional<UserRole>> findByUserRole(String role_admin);
}
