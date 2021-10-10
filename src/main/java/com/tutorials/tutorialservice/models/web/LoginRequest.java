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
@ApiModel(value = "LoginRequest", description = "Login request model")
public class LoginRequest {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
