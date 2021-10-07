package com.tutorials.tutorialservice.models.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    private String roleId;
    @NotBlank
    private String roleName;
    @NotBlank
    private String roleDescription;
}
