package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateRoleRequest", description = "Create a new role from admin model")
public class CreateRoleRequest {

    @NotBlank
    private String roleName;
    @NotBlank
    private String roleDescription;
}
