package com.tutorials.tutorialservice.services;

import com.tutorials.tutorialservice.models.web.MapRoleToUsersRequest;
import com.tutorials.tutorialservice.models.web.MapUserToRolesRequest;

public interface UserRoleService {

   void mapUserToRoles(String userNameOrEmail, MapUserToRolesRequest mapUserToRolesRequest);
   void removeRolesFromUser(String userNameOrEmail, MapUserToRolesRequest mapUserToRolesRequest);
   void mapRoleToUsers(String roleName, MapRoleToUsersRequest mapRoleToUsersRequest);
}
