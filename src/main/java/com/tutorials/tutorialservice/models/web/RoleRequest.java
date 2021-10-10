package com.tutorials.tutorialservice.models.web;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "RoleRequest", description = "Creating a new role request model")
public class RoleRequest {

    private String roleId;
    @NotBlank
    private String roleName;
    @NotBlank
    private String roleDescription;
}
