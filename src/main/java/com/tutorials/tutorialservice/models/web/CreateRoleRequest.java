package com.tutorials.tutorialservice.models.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequest {

    @NotBlank
    private String roleName;
    @NotBlank
    private String roleDescription;
}
