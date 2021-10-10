package com.tutorials.tutorialservice.controllers;

import com.tutorials.tutorialservice.models.web.RoleRequest;
import com.tutorials.tutorialservice.services.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
@Api(tags = "Roles Controller")
public class RoleController {

     private final RoleService roleService;

     @PostMapping
     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @ApiOperation(value = "Add a new role as admin")
     public ResponseEntity<RoleRequest> addRole(@ApiParam(name = "roleRequest", value = "New role request body to add",
             required = true) @RequestBody RoleRequest roleRequest) {
          RoleRequest roleRequestResponse = roleService.addNewRole(roleRequest);
          return  ResponseEntity.ok(roleRequestResponse);
     }

}
