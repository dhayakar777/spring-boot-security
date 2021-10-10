package com.tutorials.tutorialservice.models.web;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateUserRequest", description = "Create new user from admin model")
public class CreateUserRequest {

    private String userId;

    @NotBlank
    @Size(max = 40, message = "UserName length should not be grater than 40 characters")
    private String userName;

    @NotBlank
    @Size(min = 6, max = 20, message = "password length should not be between 6 and 20 characters")
    private String password;

    @NotBlank
    @Size(max = 40, message = "First Name length should not be grater than 40 characters")
    private String firstName;

    private String lastName;

    @NotBlank
    @Size(max = 40, message = "email length should not be grater than 40 characters")
    @Email
    private String email;

    private Set<String> roleNames;
}
