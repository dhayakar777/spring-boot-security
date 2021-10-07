package com.tutorials.tutorialservice.services.impl;

import com.tutorials.tutorialservice.models.web.MapRoleToUsersRequest;
import com.tutorials.tutorialservice.models.web.MapUserToRolesRequest;
import com.tutorials.tutorialservice.services.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl  implements UserRoleService {
    @Override
    public void mapUserToRoles(String userNameOrEmail, MapUserToRolesRequest mapUserToRolesRequest) {

    }

    @Override
    public void removeRolesFromUser(String userNameOrEmail, MapUserToRolesRequest mapUserToRolesRequest) {

    }

    @Override
    public void mapRoleToUsers(String roleName, MapRoleToUsersRequest mapRoleToUsersRequest) {

    }
}
