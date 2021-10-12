package com.tutorials.tutorialservice.services;


import com.tutorials.tutorialservice.models.Role;
import com.tutorials.tutorialservice.models.web.RoleRequest;
import com.tutorials.tutorialservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleRequest addNewRole(RoleRequest roleRequest) {
        Role role = Role.builder().roleName(roleRequest.getRoleName()).roleDescription(roleRequest.getRoleDescription())
                    .build();
        Role savedRole = roleRepository.save(role);
        roleRequest.setRoleId(savedRole.getRoleId());
        roleRequest.setRoleName(savedRole.getRoleName());
        roleRequest.setRoleDescription(savedRole.getRoleDescription());
        return roleRequest;
    }
}
