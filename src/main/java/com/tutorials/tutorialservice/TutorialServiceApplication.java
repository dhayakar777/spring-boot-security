package com.tutorials.tutorialservice;

import com.tutorials.tutorialservice.models.Role;
import com.tutorials.tutorialservice.models.User;
import com.tutorials.tutorialservice.models.UserRole;
import com.tutorials.tutorialservice.models.web.Roles;
import com.tutorials.tutorialservice.repository.RoleRepository;
import com.tutorials.tutorialservice.repository.RolesRepository;
import com.tutorials.tutorialservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
@EnableJpaAuditing
public class TutorialServiceApplication {

    private final RolesRepository roleRepository;

    private final UserRepository userRepository;

    public TutorialServiceApplication(RolesRepository repository, UserRepository userRepository) {
        this.roleRepository = repository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TutorialServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  /* @Bean
    CommandLineRunner runner() {
        return args -> {
            this.roleRepository.save(new UserRole(null, Roles.ROLE_USER.name()));
            this.roleRepository.save(new UserRole(null, Roles.ROLE_ADMIN.name()));
            this.roleRepository.save(new UserRole(null, Roles.ROLE_MANAGER.name()));
            this.roleRepository.save(new UserRole(null, Roles.ROLE_SUPER_ADMIN.name()));
            List<Optional<UserRole>> adminRole = roleRepository.findByUserRole("ROLE_ADMIN");
            List<Optional<UserRole>> adminRole1 = roleRepository.findByUserRole("ROLE_SUPER_ADMIN");
            Role role = Role.builder().roleId(null).roleName(adminRole.get(0).get().getUserRole()).roleDescription("Admin role").build();
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            Role role1 = Role.builder().roleId(null).roleName(adminRole1.get(0).get().getUserRole()).roleDescription("Super admin role").build();
            roleList.add(role1);
            User user = User.builder().userId(null).firstName("Admin").lastName("Istrator")
                        .userName("Administrator").email("admin.istrater@gmail.com").password(passwordEncoder().encode("admin"))
                        .roles(roleList).build();
          //  roleList.forEach(user::addRole);
            user.addRole(role);
            user.addRole(role1);
            this.userRepository.save(user);
        };
    }*/

}
